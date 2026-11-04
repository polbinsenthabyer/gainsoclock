#!/usr/bin/env bash
set -euo pipefail

release_dir="${1:?Provide the downloaded artifact directory}"
shopt -s nullglob
jars=("$release_dir"/*.jar)

if ((${#jars[@]} != 1)); then
  echo "Expected exactly one JAR; found ${#jars[@]}" >&2
  exit 1
fi

current=/opt/gainsoclock/prelive/app.jar
previous=/opt/gainsoclock/prelive/app.jar.previous
staged=/opt/gainsoclock/prelive/app.jar.next

had_previous=false
if [[ -f "$current" ]]; then
  rm -f -- "$previous"
  cp -- "$current" "$previous"
  had_previous=true
fi

install -m 0644 "${jars[0]}" "$staged"
mv -f "$staged" "$current"

restart_app(){
  sudo -n /usr/bin/systemctl restart gainsoclock-prelive.service
}

restore_previous(){
  if [[ "$had_previous" == true ]]; then
    echo "Restoring previous JAR" >&2
    mv -f -- "$previous" "$current"
    restart_app || echo "Rollback restart failed; inspect service logs" >&2
  else
    echo "No previous JAR to restore; this was the first deployment" >&2
  fi
}

if ! restart_app; then
  restore_previous
  exit 1
fi

for attempt in {1..30}; do
  if curl -fsS --max-time 2 -o /dev/null http://127.0.0.1:8082/actuator/health; then
    echo "Prelive deployment healthy"
    exit 0
  fi
  sleep 2
done

restore_previous
echo "New JAR failed its health check" >&2
exit 1
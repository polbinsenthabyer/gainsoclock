#!/usr/bin/env bash
set -euo pipefail

release_dir="${1:?Provide the downloaded artifact directory}"
shopt -s nullglob
jars=("$release_dir"/*.jar)

if ((${#jars[@]} != 1)); then
  echo "Expected exactly one JAR; found ${#jars[@]}" >&2
  exit 1
fi

current=/opt/gainsoclock/app.jar
previous=/opt/gainsoclock/app.jar.previous
staged=/opt/gainsoclock/app.jar.next

cp -p "$current" "$previous"
install -m 0644 "${jars[0]}" "$staged"
mv -f "$staged" "$current"

restart_app(){
  sudo -n /usr/bin/systemctl restart gainsoclock.service
}

restore_previous(){
  echo "Restoring previous JAR" >&2
  mv -f "$previous" "$current"
  restart_app || echo "Rollback restart failed; inspect service logs" >&2
}

if ! restart_app; then
  restore_previous
  exit 1
fi

for attempt in {1..30}; do
  if curl -fsS --max-time 2 -o /dev/null http://127.0.0.1:8081/actuator/health; then
    echo "Deployment healthy"
    exit 0
  fi
  sleep 2
done

restore_previous
echo "New JAR failed its health check" >&2
exit 1
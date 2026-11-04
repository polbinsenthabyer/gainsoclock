#!/usr/bin/env bash
set -euo pipefail

build_dir="${1:?Provide the donwloaded dist directory}"
environment="${2:?Specify prelive or live}"

case "$environment" in
  prelive)  target=/var/www/gainsoclock/prelive;;
  live)     target=/var/www/gainsoclock/live;;
  *)
    echo "Unknown environment: $environment" >&2
    exit 1
    ;;
esac

if [[ ! -f "$build_dir/index.html" ]]; then
  echo "Build directory has no index.html" >&2
  exit 1
fi


if [[ ! -d "$target" ]]; then
  echo "Target directory does not exist: $target" >&2
  exit 1
fi

rsync -r --exclude='/index.html' "$build_dir"/ "$target"/

find "$target" -type d -exec chmod 755 {} +
find "$target" -type f -exec chmod 644 {} +

install -m 0644 "$build_dir/index.html" "$target/index.html.next"
mv -f "$target/index.html.next" "$target/index.html"

echo "$environment Gains O'clock app deployed!"
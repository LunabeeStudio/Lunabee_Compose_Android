find . -depth -type d -name 'com' -print0 | while IFS= read -r -d '' dir; do
    parent=$(dirname "$dir")
    mv "$dir" "$parent/studio"
done

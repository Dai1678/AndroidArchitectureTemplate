#!/bin/bash

# exit when any command fails
set -e

if [[ $# -lt 2 ]]; then
   echo "Usage: bash customizer.sh my.new.package ApplicationName" >&2
   exit 2
fi

PACKAGE=$1
APPNAME=$2
SUBDIR=${PACKAGE//.//} # Replaces . with /

find . -type d \( -path '*/src/androidTest' -or -path '*/src/main' -or -path '*/src/test' \) -print0 | while IFS= read -r -d $'\0' dir; do
  target_dir="$dir/kotlin/$SUBDIR"
  source_dir="$dir/kotlin/dev/dai/android/architecture/template"

  echo "Creating $target_dir"
  mkdir -p "$target_dir"

  echo "Moving files to $target_dir"
  mv "$source_dir"/* "$target_dir"

  echo "Removing old $source_dir"
  rm -rf "$source_dir"
done

# Rename package and imports
echo "Renaming packages to $PACKAGE"
find ./ -type f -name "*.kt" -exec sed -i.bak "s/package dev.dai.android.architecture.template/package $PACKAGE/g" {} \;
find ./ -type f -name "*.kt" -exec sed -i.bak "s/import dev.dai.android.architecture.template/import $PACKAGE/g" {} \;

# Gradle files
find ./ -type f -name "*.kts" -exec sed -i.bak "s/dev.dai.android.architecture.template/$PACKAGE/g" {} \;

# Json files
find ./ -type f -name "*.json" -exec sed -i.bak "s/dev.dai.android.architecture.template/$PACKAGE/g" {} \;

echo "Cleaning up"
find . -name "*.bak" -type f -delete

# Rename app
if [[ $APPNAME ]]
then
    echo "Renaming app to $APPNAME"
    find ./ -type f \( -name "App.kt" -or -name "settings.gradle.kts" -or -name "*.xml" \) -exec sed -i.bak "s/App/$APPNAME/g" {} \;
    find ./ -name "App.kt" | sed "p;s/App/$APPNAME/" | tr '\n' '\0' | xargs -0 -n 2 mv
    find . -name "*.bak" -type f -delete
fi

# Remove additional files
echo "Removing additional files"
rm -rf CONTRIBUTING.md LICENSE
rm -rf .git/
echo "Done!"

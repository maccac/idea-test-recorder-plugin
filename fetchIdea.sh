#!/bin/bash 
set -e

version="2017.1.5"
zip="ideaIC-$version.tar.gz"
URL="http://download.jetbrains.com/idea/$zip"
build_dir="build_cache"

mkdir -p $build_dir
cd $build_dir

if [ ! -f $zip ];
then
   echo "File $zip not cached. Downloading from JetBrains"
   curl -L http://download.jetbrains.com/idea/$zip -o $zip
fi

tar zxf ideaIC-${version}.tar.gz

idea_path=$(find . -type d -name 'idea-IC*' | head -n 1)

if [ ! -f ${idea_path}.zip ];
   then
   # Compress to ZIP file
   cd $idea_path
   zip -r ../${idea_path}.zip *
   cd ..
fi

cd ..

# Install IDEA to Maven repo
mvn install:install-file -Dfile=$build_dir/${idea_path}.zip -DgroupId=org.jetbrains -DartifactId=org.jetbrains.intellij-ce -Dversion=${version} -Dpackaging=zip

#!/bin/bash


read -p  "请输入git的commit内容：" -t 60 commit #等待60秒
echo $commit

git add .
git commit -m "$commit"
git pull
git push

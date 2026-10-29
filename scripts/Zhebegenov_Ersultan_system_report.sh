#!/bin/bash
STUDENT="Zhebegenov Ersultan"
LOG="$HOME/zhebegenov-ersultan-student-api/logs/Zhebegenov_Ersultan_system_report.log"
{
echo "===== System report ====="
echo "Student: $STUDENT"
echo "Date: $(date '+%Y-%m-%d %H:%M:%S')"
echo "User: $(whoami)"
echo "OS: $(grep PRETTY_NAME /etc/os-release | cut -d= -f2 | tr -d '"')"
echo "Kernel: $(uname -r)"
echo "--- CPU ---"
lscpu | grep -E 'Model name|^CPU\(s\)'
echo "--- Memory ---"
free -h
echo "--- Disk usage ---"
df -h /
echo "Available disk space: $(df -h / | awk 'NR==2 {print $4}')"
echo
} | tee -a "$LOG"

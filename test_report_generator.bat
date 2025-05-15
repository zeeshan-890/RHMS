@echo off
echo Running ReportGenerator test...
cd /d %~dp0
java -cp "target/classes;target/dependency/*" com.remote_vitals.backend.reportGenerator.ReportGenerator
echo Test completed. Check the output directory for the generated PDF.
pause 
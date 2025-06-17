@echo off
echo Building Wallet Backend Application...

REM Create target directories
mkdir target\classes 2>nul

REM Download dependencies (you'll need to manually download these JARs)
echo Please download the following JAR files and place them in a 'lib' folder:
echo - spring-boot-starter-web-2.7.3.jar
echo - spring-boot-starter-data-jpa-2.7.3.jar
echo - spring-boot-starter-security-2.7.3.jar
echo - h2-2.1.214.jar
echo - lombok-1.18.24.jar
echo - jjwt-0.9.1.jar

REM Check if lib directory exists
if not exist lib (
    echo Error: Please create a 'lib' directory and add the required JAR files
    exit /b 1
)

REM Compile the application
echo Compiling Java files...
javac -cp "lib/*" -d target/classes src/main/java/com/wallet/**/*.java

REM Run the application
echo Starting the application...
java -cp "target/classes;lib/*" com.wallet.WalletApplication 
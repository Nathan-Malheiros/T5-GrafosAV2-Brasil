@echo off
chcp 65001 > nul
cd /d "%~dp0"
cls

echo ========================================
echo  T5 - Coloracao de Grafos - DSatur
echo ========================================
echo.

REM Limpar classes antigas
echo Limpando compilacoes anteriores...
if exist bin\*.class del /Q bin\*.class 2>nul
if exist src\*.class del /Q src\*.class 2>nul
if exist grafo_colorido.png del grafo_colorido.png 2>nul
echo Pronto.

echo.
echo ========================================
echo Compilando projeto...
echo ========================================

if not exist bin mkdir bin

javac -d bin -cp "bin;lib" src\*.java
if %ERRORLEVEL% neq 0 (
    echo.
    echo Erro na compilacao!
    pause
    exit /b 1
)
echo Compilacao concluida.

echo.
echo ========================================
echo Executando programa...
echo ========================================
echo.

java -cp "bin;lib" Main dados/brasil.txt

echo.
echo ========================================
echo Processo finalizado!
echo ========================================
pause

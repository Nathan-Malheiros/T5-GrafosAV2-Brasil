@echo off
chcp 65001 >nul 2>&1
echo ==========================================
echo  Versao 27 estados - incluindo DF
echo ==========================================
echo.

echo [1/2] Compilando arquivos Java...
javac -d bin src\*.java
if errorlevel 1 (
    echo ERRO na compilacao!
    pause
    exit /b 1
)
echo Compilacao concluida.
echo.

echo [2/2] Executando Main27 com brasil_27.txt...
echo.
java -cp bin Main27 "dados/brasil_27.txt"
echo.

echo ==========================================
echo  Imagem salva como: grafo_colorido_27.png
echo ==========================================
pause

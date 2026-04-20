@echo off
chcp 65001 > nul
cd /d "%~dp0"
cls

echo ========================================
echo Limpando arquivos do projeto...
echo ========================================

REM Apagar todos os arquivos .class da pasta bin
if exist bin\ (
    echo Removendo classes de bin...
    del /Q bin\*.class 2>nul
)

REM Apagar arquivos .class gerados dentro de src
if exist src\*.class (
    echo Removendo classes de src...
    del /Q src\*.class 2>nul
)

REM Apagar imagem gerada
if exist grafo_colorido.png (
    echo Removendo grafo_colorido.png...
    del grafo_colorido.png
)

REM Apagar arquivo DOT
if exist grafo_unifor.dot (
    echo Removendo grafo_unifor.dot...
    del grafo_unifor.dot
)

REM Apagar PDF gerado
if exist resultado.pdf (
    echo Removendo resultado.pdf...
    del resultado.pdf
)

echo.
echo ========================================
echo Limpeza concluida!
echo ========================================
pause

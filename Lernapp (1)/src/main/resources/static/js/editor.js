let editor;

//Initialisieren des Monaco Editor
function initializeMonacoEditor() {
//Konfigurietrt den Pfad zur Monaco-Editor-Bibliothek
require.config({paths: {vs:'https://cdnjs.cloudflare.com'}});

//Lädt und erstellt eine Editor -Instanz
require(['vs/editor/editor.main'],function(){
editor = monaco.editor.create(document.getElementById('editor'),
{
value:'//Schreibe hier deinen Java-Code\n'
language:'java'
theme:'vs-dark'
});
console.log("Monaco Editor successfully initialized");

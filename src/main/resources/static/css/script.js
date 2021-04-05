var name;
function showAlert() {
    if(confirm("Ввести имя?")) {
       name = prompt();
        alert("Имя " + name);
    }
    else alert("Имя не было введено")
}

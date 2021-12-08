$(document).ready(function() {
    let type = $("input[name='variable_number']:checked").val();
    if (type != null) addTable(type);
    $("input[name='variable_number']").change(function () {
        type = $("input[name='variable_number']:checked").val();
        addTable(type);
    });
});

function addTable(type) {
    $("#input-func-value").html("<h4 class=\"border-bottom\"><label>Значения функции:</label></h4>");
    let table = document.createElement("table");
    table.classList.add("table");
    table.classList.add("table-hover");
    table.append(tableHead(type));
    table.append(tableBody(type));

    $("#input-func-value").append(table);
    $("#send").html("<input type=\"submit\" value=\"Send\" class=\"btn btn-primary\">");
}

function tableHead(type) {
    let table_head = document.createElement("thead");
    if (type >= 2) {
        let headers = document.createElement("tr");
        let head =  document.createElement("th");
        let th1 = document.createElement("th"); th1.append("A");
        let th2 = document.createElement("th"); th2.append("B");
        headers.append(th1, th2);
        if (type >= 3) {
            let th3 = document.createElement("th"); th3.append("C");
            headers.append(th3);
            if (type == 4) {
                let th4 = document.createElement("th"); th4.append("D");
                headers.append(th4);
                head.append("f(A, B, C, D)");
            }
            else head.append("f(A, B, C)");
        }
        else head.append("f(A, B)");
        headers.append(head);
        table_head.append(headers);
    }
    return table_head;
}

function tableBody(type) {
    let table_body = document.createElement("tbody");
    for (let i = 0; i < Math.pow(2, type); i++) {
        let x = i;
        let row = document.createElement("tr");
        for (let j = 0; j < type; j++) {
            let cell = document.createElement("td"); cell.append(x % 2);
            row.prepend(cell);
            x = Math.floor(x / 2);
        }
        let last_cell = document.createElement("td");
        last_cell.classList.add("col-4");
        last_cell.innerHTML = "<input class=\"form-control\" type=\"number\" max=\"1\" min=\"0\" name=\"" + i + "\">";
        row.append(last_cell);
        table_body.append(row);
    }
    return table_body;
}
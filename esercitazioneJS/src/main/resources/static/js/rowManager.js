document.addEventListener("DOMContentLoaded", function() {

    document.querySelectorAll(".deleteButton").forEach(addDeleteEvent);

    document.getElementById("addServiceButton").addEventListener("click", function() {
        document.getElementById("newServiceForm").style.display = "block";
    });

    document.getElementById("newServiceForm").addEventListener("submit", function(event) {
        event.preventDefault();

        const title = document.getElementById("title").value;
        const price = document.getElementById("price").value;
        const manager = document.getElementById("manager").value;

        addNewRow(title, price, manager);

        document.getElementById("newServiceForm").style.display = "none";
        document.getElementById("newServiceForm").reset();
    });

    function addNewRow(title, price, manager) {
        const tableBody = document.getElementById("serviceTable").querySelector("tbody");
        const newRow = tableBody.insertRow();

        newRow.insertCell(0).textContent = title;
        newRow.insertCell(1).innerHTML = `<span class="badge bg-success">€${price}</span>`;
        newRow.insertCell(2).textContent = manager;

        const deleteButtonCell = newRow.insertCell(3);
        deleteButtonCell.innerHTML = `<button class="btn btn-danger btn-sm deleteButton"><i class="fas fa-trash"></i></button>`;
        addDeleteEvent(deleteButtonCell.querySelector(".deleteButton"));
    }

    function addDeleteEvent(button) {
        button.addEventListener("click", function() {
            this.closest("tr").remove();
        });
    }
});

var customers = []
var customerResourceUrl = ""

// post create entity request
function requestCreatingNewCustomer(customerName) {
    $.ajax({
        type: "POST",
        url: customerResourceUrl,
        headers: {
            "Accept": "application/json; charset=utf-8",
            "Content-type": "application/json; charset=utf-8"
        },
        data: JSON.stringify({name: customerName})
    })
    .done((data, status) => {
        console.log({"status": status, "data": data})
        fetchCustomersTable()
    })
    .fail(err => {
        console.error(err)
        alert("ERROR, see logs")
    })
}

// fetch entities for table
function fetchCustomersTable() {
    $.ajax({
        url: customerResourceUrl,
        headers: {
            Accept : "application/json; charset=utf-8",
        }
    })
    .done((data, status) => {
        console.log({"status": status, "data": data})
        customers = data
        renderCustomerTable()
    })
    .fail(err => {
        console.error(err)
        alert("ERROR, see logs")
    })
}

// renderer of table click callback
function applySelectionFromCustomersTable(customerId) {
    updateOrderAvailability_customerId(customerId.substring(10))
    $(".customer-highlight").removeClass("customer-highlight");
    $(`#${customerId}`).addClass('customer-highlight');
    $("#orders-table .customer-column").filter(`[ref='${customerId.substring(10)}']`).addClass('customer-highlight');
}

// renderer of fetch callback
function renderCustomerTable() {
    var innerMap = c => `<tr id="customers-${c.id}">
        <td class="id-column">${c.id}</td>
        <td class="name-column">${c.name}</td>
    </tr>`
    var customersRenderTable = customers.map(innerMap)
    $("#customers-table tbody").html(customersRenderTable.join())
}


// add button callback
function addCustomer() {
    var v = $("#input-customer-name").val()
    if (v === "") return
    requestCreatingNewCustomer(v)
    $("#input-customer-name").val("")
}

// callback for table click
$(document).ready(function() {
    $('#customers-table').on('click', 'tbody tr', function(event) {
        applySelectionFromCustomersTable($(this).attr("id"))
    });
    $("#add-customer-button").click(addCustomer)
})
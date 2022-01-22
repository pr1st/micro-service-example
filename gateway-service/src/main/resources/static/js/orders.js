var orders = []

var orderResourceUrl = ""

var selected = {
    customerId: null,
    productId: null,
}

// post create entity request
function requestCreatingNewOrder(customerId, productId) {
    $.ajax({
        type: "POST",
        url: orderResourceUrl,
        headers: {
            "Accept": "application/json; charset=utf-8",
            "Content-type": "application/json; charset=utf-8"
        },
        data: JSON.stringify({"customerId": customerId, "productId": productId})
    })
    .done((data, status) => {
        console.log({"status": status, "data": data})
        fetchOrdersTable()
    })
    .fail(err => {
        console.error(err)
        alert("ERROR, see logs")
    })
}

// fetch entities for table
function fetchOrdersTable() {
    $.ajax({
        url: orderResourceUrl,
        headers: {
            Accept : "application/json; charset=utf-8",
        }
    })
    .done((data, status) => {
        console.log({"status": status, "data": data})
        orders = data
        renderOrderTable()
    })
    .fail(err => {
        console.error(err)
        alert("ERROR, see logs")
    })
}

// renderer of table click callback
function applySelectionFromOrdersTable(orderId, customerId, productId) {
    $(".order-highlight").removeClass("order-highlight");
    $(`#${orderId}`).addClass('order-highlight');
    $(`#customers-${customerId} .name-column`).addClass('order-highlight');
    $(`#products-${productId} .name-column`).addClass('order-highlight');
}

// renderer of fetch callback
function renderOrderTable() {
    var innerMap = c => `<tr id="orders-${c.id}">
        <td class="id-column">${c.id}</td>
        <td class="customer-column" ref="${c.customerId}">${customers.find(p => p.id === c.customerId)?.name}</td>
        <td class="product-column" ref="${c.productId}">${products.find(p => p.id === c.productId)?.title}</td>
    </tr>`
    var ordersRenderTable = orders.map(innerMap)
    $("#orders-table tbody").html(ordersRenderTable.join())
}

function updateAvailabilityAddOrderButton() {
    if (selected.customerId === null || selected.productId === null) {
        $("#add-order-button").addClass("disabled");
    } else {
        $("#add-order-button").removeClass("disabled");
    }
}
function updateOrderAvailability_customerId(customerId) {
    selected.customerId = customerId
    updateAvailabilityAddOrderButton()
}

function updateOrderAvailability_productId(productId) {
    selected.productId = productId
    updateAvailabilityAddOrderButton()
}

// add button callback
function addOrder() {
    if (selected.customerId === null || selected.productId === null) return
    requestCreatingNewOrder(selected.customerId, selected.productId)
}

// callback for table click
$(document).ready(function() {
    $('#orders-table').on('click', 'tbody tr', function(event) {
        applySelectionFromOrdersTable($(this).attr("id"),
                                    $(".customer-column", this).attr("ref"),
                                    $(".product-column", this).attr("ref")
        )
    });
    $("#add-order-button").click(addOrder)
})
var products = []
var productResourceUrl = "http://localhost:8003/api/v1/products"

// post create entity request
function requestCreatingNewProduct(productTitle) {
    $.ajax({
        type: "POST",
        url: productResourceUrl,
        headers: {
            "Accept": "application/json; charset=utf-8",
            "Content-type": "application/json; charset=utf-8"
        },
        data: JSON.stringify({"title": productTitle})
    })
    .done((data, status) => {
        console.log({"status": status, "data": data})
        fetchProductsTable()
    })
    .fail(err => {
        console.error(err)
        alert("ERROR, see logs")
    })
}

// fetch entities for table
function fetchProductsTable() {
    $.ajax({
        url: productResourceUrl,
        headers: {
            Accept : "application/json; charset=utf-8",
        }
    })
    .done((data, status) => {
        console.log({"status": status, "data": data})
        products = data
        renderProductTable()
    })
    .fail(err => {
        console.error(err)
        alert("ERROR, see logs")
    })
}

// renderer of table click callback
function applySelectionFromProductsTable(productId) {
    updateOrderAvailability_productId(productId.substring(9))
    $(".product-highlight").removeClass("product-highlight");
    $(`#${productId}`).addClass('product-highlight');
    $("#orders-table .product-column").filter(`[ref="${productId.substring(9)}"]`).addClass('product-highlight');
}

// renderer of fetch callback
function renderProductTable() {
    var innerMap = c => `<tr id="products-${c.id}">
        <td class="id-column">${c.id}</td>
        <td class="name-column">${c.title}</td>
    </tr>`
    var productsRenderTable = products.map(innerMap)
    $("#products-table tbody").html(productsRenderTable.join())
}


// add button callback
function addProduct() {
    var v = $("#input-product-name").val()
    if (v === "") return
    requestCreatingNewProduct(v)
    $("#input-product-name").val("")
}

// callback for table click
$(document).ready(function() {
    $('#products-table').on('click', 'tbody tr', function(event) {
        applySelectionFromProductsTable($(this).attr("id"))
    });
    $("#add-product-button").click(addProduct)
})
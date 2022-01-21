
function fetchActions() {
    // TODO make request
    fetchCustomersTable()
    fetchProductsTable()
    fetchOrdersTable()
}

function addActionButton(name, onclick) {
    $("#global-buttons")
        .append(`<a role="button" class="btn btn-outline-primary">${name}</a>`)
        .click(onclick)
}

$(document).ready(function() {
    $("#reset-highlights").click(function() {
        $(".customer-highlight").removeClass("customer-highlight");
        $(".product-highlight").removeClass("product-highlight");
        $(".order-highlight").removeClass("order-highlight");
        updateOrderAvailability_customerId(null)
        updateOrderAvailability_productId(null)
    })
    fetchActions()
})

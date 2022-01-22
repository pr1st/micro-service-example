var apiUrl = "http://localhost:8080/api"

function fetchActions() {
    $.ajax({
        type: "GET",
        url: apiUrl,
        headers: {
            "Accept": "application/hal+json; charset=utf-8"
        },
    })
    .done((data, status) => {
        console.log({"status": status, "data": data})
        var links = data["_links"]

        customerResourceUrl = links["customers"].href
        orderResourceUrl = links["orders"].href
        productResourceUrl = links["products"].href

        fetchCustomersTable()
        fetchProductsTable()
        fetchOrdersTable()

        delete links["customers"]
        delete links["orders"]
        delete links["products"]
        delete links["self"]

        console.log(links)
        for (l in links) {
            addSupportedActionButton(l, links[l].href)
        }
    })
    .fail(err => {
        console.error(err)
        alert("ERROR, see logs")
    })
}

function addSupportedActionButton(rel, href) {
    if (rel === "customerOrders") {
        addActionButton("customer-orders-button", "Customer orders", () => {
            var a = $("#customers-table .customer-highlight")?.attr("id")?.substring(10)
            if (a !== undefined) {
                var actionLink = href.replace("{client_id}", a)
                console.log(actionLink)
                window.open(actionLink, '_blank');
            }
        })
    }
}

function addActionButton(id ,name, onclick) {
    $("#global-buttons")
       .append(`<a id="${id}" role="button" class="btn btn-outline-primary">${name}</a>`)
    $(`#${id}`).click(onclick)
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

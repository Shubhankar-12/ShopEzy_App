var shopName = "Lesile Fashion"
var shopAddress = "Rua da Paz, nº 10"
var shopPhone = "21-99999-9999"
var shopEmail = "Lesilealexandder@gmail.com"
var customerName = "Alexandre"
var customerPhone = "21-99999-9999"
var customerEmail = "someone@gmail.com"
var invoiceNumber = "#123456789"
var invoiceDate = "15-08-2020"
var payment_mode = "Dinheiro"
var GrandTotal = 0
var totalItem = 0
var shopPicData = "mail.svg"
var accountantName = "shivay"
var accountantSignData = "mail.svg"
var data =""
function setAllData(){
    document.getElementById("shop_name").innerHTML = shopName
    document.getElementById("shop_address").innerHTML = shopAddress
    document.getElementById("phone_shop").innerHTML = shopPhone
    document.getElementById("email_shop").innerHTML = shopEmail
    document.getElementById("customer_name").innerHTML = customerName
    document.getElementById("customer_phone").innerHTML = customerPhone
    document.getElementById("customer_email").innerHTML = customerEmail
    document.getElementById("invoice_number").innerHTML = invoiceNumber
    document.getElementById("invoice_date").innerHTML = invoiceDate
    document.getElementById("accountant_name").innerHTML = accountantName
    document.getElementById("total_price").innerHTML = GrandTotal
    document.getElementById("total_items").innerHTML = totalItem
    document.getElementById("payment_method_info").innerHTML = payment_mode

    document.getElementById("shop_profile_pic").src = shopPicData
    document.getElementById("accountant_signature").src = accountantSignData

    //insert the html
    document.getElementById("table_body").innerHTML = data +
    document.getElementById("table_body").innerHTML

}
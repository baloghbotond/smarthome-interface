function getActualTime() {
  var today = new Date();
  var h = today.getHours();
  var m = today.getMinutes();
  var s = today.getSeconds();
  m = checkTime(m);
  s = checkTime(s);
  document.getElementById("actualTime").innerHTML = h + ":" + m + ":" + s;
  var t = setTimeout(getActualTime, 500);
}
function getActualDate() {
  var today = new Date();
  var year = today.getFullYear();
  var dayOfMonth = today.getDate();
  var month = checkTime(today.getMonth() + 1);
  dom = checkTime(dayOfMonth);
  document.getElementById("actualDate").innerHTML = month + "." + dom + ".";
  var p = setTimeout(getActualDate, 30000);
}
function checkTime(i) {
  if (i < 10) {
    i = "0" + i;
  } // add zero in front of numbers < 10
  return i;
}
function getNameOfMonth(i) {
  var months = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December"
  ];
  return months[i];
}

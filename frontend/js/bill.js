function initBillPage(){
  requireAuth();
}

async function getBill(){
  const resNo = document.getElementById("resNo").value.trim();
  const msg = document.getElementById("msg");
  const area = document.getElementById("billArea");
  msg.innerHTML = "";
  area.innerHTML = "";

  if(!resNo){
    msg.innerHTML = `<div class="msg err">Enter reservation number.</div>`;
    return;
  }

  try{
    const b = await apiFetch(`/api/billing/${encodeURIComponent(resNo)}`);
    area.innerHTML = `
      <div class="card" style="margin-top:14px">
	  <div class="bill-title">
	    <h1 style="font-size:18px;margin:0">Ocean View Resort — Bill</h1>
	    <span class="tag">Printable Invoice</span>
	  </div>
        <table class="table">
          <tr><th>Reservation No</th><td>${b.reservationNo}</td></tr>
          <tr><th>Guest Name</th><td>${b.guestName}</td></tr>
          <tr><th>Contact</th><td>${b.contactNo}</td></tr>
          <tr><th>Room Type</th><td>${b.roomType}</td></tr>
          <tr><th>Check-in</th><td>${b.checkIn}</td></tr>
          <tr><th>Check-out</th><td>${b.checkOut}</td></tr>
          <tr><th>Total Nights</th><td>${b.totalNights}</td></tr>
          <tr><th>Rate per Night</th><td>LKR ${b.ratePerNight}</td></tr>
          <tr><th><b>Total Amount</b></th><td><b>LKR ${b.totalAmount}</b></td></tr>
        </table>
        <div class="small">Use the Print button to print this bill.</div>
      </div>
    `;
    msg.innerHTML = `<div class="msg ok">Bill generated successfully.</div>`;
  }catch(e){
    msg.innerHTML = `<div class="msg err">${e.message}</div>`;
  }
}

function printBill(){
  const area = document.getElementById("billArea");
  if(!area.innerText.trim()){
    document.getElementById("msg").innerHTML = `<div class="msg err">Generate a bill first.</div>`;
    return;
  }
  window.print();
}
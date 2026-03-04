function initViewPage(){
  requireAuth();
}

async function searchReservation(){
  const resNo = document.getElementById("resNo").value.trim();
  const msg = document.getElementById("msg");
  const result = document.getElementById("result");
  msg.innerHTML = "";
  result.innerHTML = "";

  if (!resNo){
    msg.innerHTML = `<div class="msg err">Enter reservation number.</div>`;
    return;
  }

  try{
    const r = await apiFetch(`/api/reservations/${encodeURIComponent(resNo)}`);
    result.innerHTML = `
      <table class="table">
        <tr><th>Reservation No</th><td>${r.reservationNo}</td></tr>
        <tr><th>Guest Name</th><td>${r.guestName}</td></tr>
        <tr><th>Room Type</th><td>${r.roomType}</td></tr>
        <tr><th>Check-in</th><td>${r.checkIn}</td></tr>
        <tr><th>Check-out</th><td>${r.checkOut}</td></tr>
        <tr><th>Nights</th><td>${r.totalNights}</td></tr>
        <tr><th>Total Amount</th><td>LKR ${r.totalAmount}</td></tr>
        <tr><th>Status</th><td><span class="badge">${r.status}</span></td></tr>
      </table>
    `;
  }catch(e){
    msg.innerHTML = `<div class="msg err">${e.message}</div>`;
  }
}
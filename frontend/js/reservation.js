function initReservationPage() {
  requireAuth();
  loadRoomTypes();
}

async function loadRoomTypes() {
  const select = document.getElementById("roomType");
  select.innerHTML = `<option>Loading...</option>`;
  try {
    const types = await apiFetch("/api/room-types");
    select.innerHTML = "";
    types.forEach(t => {
      const opt = document.createElement("option");
      opt.value = t.id;
      opt.textContent = `${t.name} (LKR ${t.ratePerNight} / night, cap ${t.capacity})`;
      select.appendChild(opt);
    });
  } catch (e) {
    select.innerHTML = `<option>Error loading room types</option>`;
  }
}

async function createReservation() {
  const msg = document.getElementById("msg");
  msg.innerHTML = "";

  const payload = {
    guestName: document.getElementById("guestName").value.trim(),
    guestAddress: document.getElementById("guestAddress").value.trim(),
    contactNo: document.getElementById("contactNo").value.trim(),
    roomTypeId: Number(document.getElementById("roomType").value),
    checkIn: document.getElementById("checkIn").value.trim(),
    checkOut: document.getElementById("checkOut").value.trim()
  };

  if (!payload.guestName || !payload.guestAddress || !payload.contactNo || !payload.checkIn || !payload.checkOut) {
    msg.innerHTML = `<div class="msg err">Please fill all fields.</div>`;
    return;
  }

  try {
    const res = await apiFetch("/api/reservations", {
      method: "POST",
      body: JSON.stringify(payload)
    });

    msg.innerHTML = `
      <div class="msg ok">
        Reservation created successfully.<br/>
        <b>Reservation No:</b> ${res.reservationNo}<br/>
        <span class="small">You can view it in “View Reservation” or generate the bill.</span>
      </div>
    `;
  } catch (e) {
    msg.innerHTML = `<div class="msg err">${e.message}</div>`;
  }
}

function fillExample(){
  document.getElementById("guestName").value = "Kavindu";
  document.getElementById("guestAddress").value = "Galle, Sri Lanka";
  document.getElementById("contactNo").value = "0771234567";
  document.getElementById("checkIn").value = "2026-03-10";
  document.getElementById("checkOut").value = "2026-03-12";
}
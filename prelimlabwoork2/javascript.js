// Hardcoded login
const VALID_USERNAME = "renze";
const VALID_PASSWORD = "1234abcd";

// Beep sound
const beep = new Audio("beep.mp3");

// Attendance storage
let attendanceRecords = [];

// Form submission
document.getElementById("loginForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    if (username === VALID_USERNAME && password === VALID_PASSWORD) {
        successfulLogin(username);
    } else {
        failedLogin();
    }
});

function successfulLogin(username) {
    const now = new Date();
    const timestamp = formatTimestamp(now);

    document.getElementById("message").textContent =
        "Welcome, " + username + "!";
    document.getElementById("timestamp").textContent =
        "Login Time: " + timestamp;

    attendanceRecords.push({
        user: username,
        time: timestamp
    });

    generateAttendanceFile(username, timestamp);
}

function failedLogin() {
    document.getElementById("message").textContent =
        "Incorrect username or password!";
    document.getElementById("timestamp").textContent = "";
    beep.play();
}

function formatTimestamp(date) {
    const mm = String(date.getMonth() + 1).padStart(2, "0");
    const dd = String(date.getDate()).padStart(2, "0");
    const yyyy = date.getFullYear();
    const hh = String(date.getHours()).padStart(2, "0");
    const min = String(date.getMinutes()).padStart(2, "0");
    const ss = String(date.getSeconds()).padStart(2, "0");

    return `${mm}/${dd}/${yyyy} ${hh}:${min}:${ss}`;
}

function generateAttendanceFile(username, timestamp) {
    const text =
        "ATTENDANCE SUMMARY\n\n" +
        "Username: " + username + "\n" +
        "Timestamp: " + timestamp + "\n";

    const blob = new Blob([text], { type: "text/plain" });
    const link = document.createElement("a");

    link.href = URL.createObjectURL(blob);
    link.download = "attendance_summary.txt";
    link.click();
}

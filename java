// 🔥 PASTE YOUR FIREBASE CONFIG HERE
const firebaseConfig = {
  // YOUR FIREBASE CONFIG
};<script type="module">  
  // Import the functions you need from the SDKs you need  
  import { initializeApp } from "https://www.gstatic.com/firebasejs/12.4.0/firebase-app.js";  
  import { getAnalytics } from "https://www.gstatic.com/firebasejs/12.4.0/firebase-analytics.js";  
  // TODO: Add SDKs for Firebase products that you want to use  
  // https://firebase.google.com/docs/web/setup#available-libraries  
  
  // Your web app's Firebase configuration  
  // For Firebase JS SDK v7.20.0 and later, measurementId is optional  
  const firebaseConfig = {  
    apiKey: "AIzaSyAv9hUbu93tMNTulLJRV97VRwl9fbftj-U",  
    authDomain: "gift-kapokola-quotes-916fd.firebaseapp.com",  
    projectId: "gift-kapokola-quotes-916fd",  
    storageBucket: "gift-kapokola-quotes-916fd.firebasestorage.app",  
    messagingSenderId: "499228773823",  
    appId: "1:499228773823:web:5d64b6c0094bf6974be50a",  
    measurementId: "G-T6CMP20V95"  
  };  
  
  // Initialize Firebase  
  const app = initializeApp(firebaseConfig);  
  const analytics = getAnalytics(app);  
</script>

// Initialize Firebase
const app = firebase.initializeApp(firebaseConfig);
const db = firebase.firestore();
const storage = firebase.storage();

const quotesContainer = document.getElementById("quotes-container");
const searchInput = document.getElementById("search");

function displayQuote(doc) {
  const div = document.createElement("div");
  div.className = "quote-card";
  div.innerHTML = `
    <p class="quote-text">"${doc.data().text}"</p>
    <p class="quote-author">- ${doc.data().author}</p>
    ${doc.data().image ? `<img src="${doc.data().image}" alt="Quote Image">` : ""}
    <div class="quote-actions">
      <button class="like-btn">❤️</button>
      <button class="comment-btn">💬</button>
      <button class="share-btn">🔗</button>
      <button class="copy-btn">📋</button>
    </div>
  `;
  quotesContainer.appendChild(div);

  div.querySelector(".copy-btn").addEventListener("click", () => {
    navigator.clipboard.writeText(`"${doc.data().text}" - ${doc.data().author}`);
    alert("Copied!");
  });
}

// Load all quotes
db.collection("quotes").orderBy("timestamp", "desc").onSnapshot(snapshot => {
  quotesContainer.innerHTML = "";
  snapshot.forEach(doc => displayQuote(doc));
});

// Search functionality
searchInput.addEventListener("input", (e) => {
  const searchTerm = e.target.value.toLowerCase();
  db.collection("quotes").get().then(snapshot => {
    quotesContainer.innerHTML = "";
    snapshot.forEach(doc => {
      if (doc.data().text.toLowerCase().includes(searchTerm) || doc.data().author.toLowerCase().includes(searchTerm)) {
        displayQuote(doc);
      }
    });
  });
});
// Saves website forms to Cloud Firestore (project: sanatan-sadhak).
// The web config below is public by design; access is controlled by firestore.rules,
// which only allow creating validated documents — nobody can read them from the site.
import { initializeApp } from 'https://www.gstatic.com/firebasejs/10.12.2/firebase-app.js';
import { getFirestore, collection, addDoc, serverTimestamp } from 'https://www.gstatic.com/firebasejs/10.12.2/firebase-firestore.js';

const app = initializeApp({
  apiKey: 'AIzaSyAkqz8QyrU5OXXOEow6ZBE-Pnab5V6pguc',
  authDomain: 'sanatan-sadhak.firebaseapp.com',
  projectId: 'sanatan-sadhak',
  storageBucket: 'sanatan-sadhak.firebasestorage.app',
  messagingSenderId: '420062423164',
  appId: '1:420062423164:web:001a29d3ada6e00a7dc1f9'
});
const db = getFirestore(app);

const EMAIL_RE = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;

function setStatus(form, text, kind) {
  const s = form.querySelector('.status');
  if (!s) return;
  s.textContent = text;
  s.className = 'status' + (kind ? ' ' + kind : '');
}

function setBusy(btn, busy, label) {
  btn.disabled = busy;
  btn.innerHTML = busy ? '<span class="spinner" aria-hidden="true"></span> Sending…' : label;
}

/**
 * Wire a form to a Firestore collection.
 * build(fd) returns the document fields (without createdAt) or throws an Error with a user message.
 */
function wire(formId, collectionName, build, successMsg) {
  const form = document.getElementById(formId);
  if (!form) return;
  const btn = form.querySelector('button[type="submit"]');
  const label = btn.innerHTML;

  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    const fd = new FormData(form);
    if (fd.get('website')) return; // honeypot: bots fill hidden fields

    let data;
    try { data = build(fd); } catch (err) { setStatus(form, err.message, 'err'); return; }

    setBusy(btn, true, label);
    setStatus(form, '', '');
    try {
      await addDoc(collection(db, collectionName), {
        ...data,
        source: 'website',
        page: location.pathname,
        createdAt: serverTimestamp()
      });
      form.reset();
      setStatus(form, successMsg, 'ok');
      window.showToast && window.showToast(successMsg);
    } catch (err) {
      console.error(err);
      setStatus(form, 'Something went wrong. Please try again, or email support@chaloone.com.', 'err');
    } finally {
      setBusy(btn, false, label);
    }
  });
}

const clean = (v, max) => String(v || '').trim().slice(0, max);

wire('early-access-form', 'early_access', (fd) => {
  const email = clean(fd.get('email'), 200).toLowerCase();
  if (!EMAIL_RE.test(email)) throw new Error('Please enter a valid email address.');
  if (!fd.get('consent')) throw new Error('Please agree to be contacted about early access.');
  return {
    name: clean(fd.get('name'), 100),
    email,
    city: clean(fd.get('city'), 100),
    interest: clean(fd.get('interest'), 60),
    consent: true
  };
}, 'Jai Shri Ram! You are on the early-access list. We will email you when it opens.');

wire('deletion-form', 'deletion_requests', (fd) => {
  const email = clean(fd.get('email'), 200).toLowerCase();
  if (!EMAIL_RE.test(email)) throw new Error('Please enter the email address linked to your account.');
  const scope = clean(fd.get('scope'), 20);
  if (!['account', 'data_only'].includes(scope)) throw new Error('Please choose what you want deleted.');
  if (!fd.get('confirm')) throw new Error('Please confirm you are the account owner.');
  return {
    name: clean(fd.get('name'), 100),
    email,
    scope,
    reason: clean(fd.get('reason'), 1000),
    status: 'pending'
  };
}, 'Request received. We will confirm by email and complete it within 30 days.');

wire('contact-form', 'contact_messages', (fd) => {
  const email = clean(fd.get('email'), 200).toLowerCase();
  if (!EMAIL_RE.test(email)) throw new Error('Please enter a valid email address.');
  const message = clean(fd.get('message'), 2000);
  if (message.length < 5) throw new Error('Please write a short message.');
  return { name: clean(fd.get('name'), 100), email, topic: clean(fd.get('topic'), 40), message };
}, 'Thank you! We received your message and will reply by email.');

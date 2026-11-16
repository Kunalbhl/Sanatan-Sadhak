// Shared page behaviour: mobile menu, reveal-on-scroll, footer year, toast, demo japa counter.
(function () {
  const btn = document.querySelector('.menu-btn');
  const links = document.querySelector('.nav-links');
  if (btn && links) {
    btn.addEventListener('click', () => {
      const open = links.classList.toggle('open');
      btn.setAttribute('aria-expanded', String(open));
    });
    links.addEventListener('click', (e) => { if (e.target.tagName === 'A') links.classList.remove('open'); });
  }

  const els = document.querySelectorAll('.reveal');
  if ('IntersectionObserver' in window) {
    const io = new IntersectionObserver((entries) => {
      entries.forEach((en) => { if (en.isIntersecting) { en.target.classList.add('in'); io.unobserve(en.target); } });
    }, { threshold: 0.12 });
    els.forEach((el) => io.observe(el));
  } else {
    els.forEach((el) => el.classList.add('in'));
  }

  document.querySelectorAll('[data-year]').forEach((el) => { el.textContent = new Date().getFullYear(); });

  // Demo japa counter in the phone mock: tap to count to 108.
  const ring = document.querySelector('.ring');
  if (ring) {
    let n = 0;
    const num = ring.querySelector('.num');
    const label = ring.querySelector('small');
    const tick = () => {
      n = n >= 108 ? 0 : n + 1;
      num.textContent = n;
      ring.style.background = `conic-gradient(var(--saffron) ${(n / 108) * 100}%, rgba(255,170,80,.1) 0)`;
      label.textContent = n === 108 ? 'Mala complete 🙏' : 'of 108';
      if (navigator.vibrate) navigator.vibrate(n === 108 ? [40, 60, 40] : 8);
    };
    ring.addEventListener('click', tick);
    ring.addEventListener('keydown', (e) => { if (e.key === 'Enter' || e.key === ' ') { e.preventDefault(); tick(); } });
  }
})();

window.showToast = function (msg) {
  let t = document.querySelector('.toast');
  if (!t) { t = document.createElement('div'); t.className = 'toast'; t.setAttribute('role', 'status'); document.body.appendChild(t); }
  t.textContent = msg;
  t.classList.add('show');
  clearTimeout(window.__toastTimer);
  window.__toastTimer = setTimeout(() => t.classList.remove('show'), 3200);
};

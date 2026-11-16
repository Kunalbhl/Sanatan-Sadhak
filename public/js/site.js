// Shared behaviour: menu, reveal-on-scroll, diya sparks, feature tour tabs, shloka rotation, toast.
(function () {
  const reduce = window.matchMedia && matchMedia('(prefers-reduced-motion: reduce)').matches;
  const small = window.innerWidth < 760;

  const btn = document.querySelector('.menu-btn'), links = document.querySelector('.nav-links');
  if (btn && links) {
    btn.addEventListener('click', () => { const o = links.classList.toggle('open'); btn.setAttribute('aria-expanded', String(o)); btn.textContent = o ? '✕' : '☰'; });
    links.addEventListener('click', (e) => { if (e.target.tagName === 'A') { links.classList.remove('open'); btn.textContent = '☰'; } });
  }

  const els = document.querySelectorAll('.reveal');
  if ('IntersectionObserver' in window && !reduce) {
    const io = new IntersectionObserver((en) => en.forEach((x) => { if (x.isIntersecting) { x.target.classList.add('in'); io.unobserve(x.target); } }), { threshold: 0.1 });
    els.forEach((el) => io.observe(el));
  } else els.forEach((el) => el.classList.add('in'));

  document.querySelectorAll('[data-year]').forEach((el) => { el.textContent = new Date().getFullYear(); });

  // Drifting diya sparks (fewer on phones, none with reduced motion)
  const bg = document.querySelector('.bg-global');
  if (bg && !reduce) {
    const n = small ? 6 : 14;
    for (let i = 0; i < n; i++) {
      const s = document.createElement('span');
      s.className = 'spark';
      s.style.left = (Math.random() * 100).toFixed(1) + 'vw';
      s.style.setProperty('--dx', (Math.random() * 80 - 40).toFixed(0) + 'px');
      s.style.animationDuration = (14 + Math.random() * 14).toFixed(1) + 's';
      s.style.animationDelay = (-Math.random() * 20).toFixed(1) + 's';
      bg.appendChild(s);
    }
  }

  // Feature tour tabs (arrow keys supported)
  const tabs = [...document.querySelectorAll('.tour-tabs [role="tab"]')];
  function select(t) {
    tabs.forEach((x) => { const on = x === t; x.setAttribute('aria-selected', String(on)); x.tabIndex = on ? 0 : -1; document.getElementById(x.getAttribute('aria-controls')).hidden = !on; });
  }
  tabs.forEach((t, i) => {
    t.tabIndex = i === 0 ? 0 : -1;
    t.addEventListener('click', () => select(t));
    t.addEventListener('keydown', (e) => {
      const d = (e.key === 'ArrowDown' || e.key === 'ArrowRight') ? 1 : (e.key === 'ArrowUp' || e.key === 'ArrowLeft') ? -1 : 0;
      if (d) { e.preventDefault(); const n = tabs[(i + d + tabs.length) % tabs.length]; select(n); n.focus(); }
    });
  });

  // Shloka of the day (rotates by date, "Next" cycles)
  const SH = [
    ['ॐ त्र्यम्बकं यजामहे सुगन्धिं पुष्टिवर्धनम् ।<br>उर्वारुकमिव बन्धनान्मृत्योर्मुक्षीय माऽमृतात् ॥', 'We worship the three-eyed One, fragrant, who nourishes all. Like the ripe cucumber from its stem, may He free us from death and not from immortality. (Rigveda 7.59.12)'],
    ['कर्मण्येवाधिकारस्ते मा फलेषु कदाचन ।<br>मा कर्मफलहेतुर्भूर्मा ते सङ्गोऽस्त्वकर्मणि ॥', 'You have a right to your actions, never to their fruits. Do not let the fruit be your motive, nor be attached to inaction. (Bhagavad Gita 2.47)'],
    ['ॐ भूर्भुवः स्वः तत्सवितुर्वरेण्यं<br>भर्गो देवस्य धीमहि धियो यो नः प्रचोदयात् ॥', 'We meditate on the radiant light of the divine Savitr. May it inspire our understanding. (Gayatri Mantra, Rigveda 3.62.10)'],
    ['असतो मा सद्गमय । तमसो मा ज्योतिर्गमय ।<br>मृत्योर्मा अमृतं गमय ॥', 'Lead me from the unreal to the real, from darkness to light, from death to immortality. (Brihadaranyaka Upanishad 1.3.28)'],
    ['सर्वे भवन्तु सुखिनः सर्वे सन्तु निरामयाः ।<br>सर्वे भद्राणि पश्यन्तु मा कश्चिद्दुःखभाग्भवेत् ॥', 'May all be happy, may all be free from illness. May all see what is auspicious, and may no one suffer.'],
    ['योगस्थः कुरु कर्माणि सङ्गं त्यक्त्वा धनञ्जय ।<br>सिद्ध्यसिद्ध्योः समो भूत्वा समत्वं योग उच्यते ॥', 'Established in yoga, do your work, Arjuna, giving up attachment and staying even in success and failure. Evenness of mind is called yoga. (Bhagavad Gita 2.48)']
  ];
  const sk = document.querySelector('[data-shloka-sk]'), tr = document.querySelector('[data-shloka-tr]');
  if (sk && tr) {
    let i = Math.floor(Date.now() / 86400000) % SH.length;
    const show = () => { sk.innerHTML = SH[i][0]; tr.textContent = SH[i][1]; };
    show();
    const nb = document.querySelector('[data-shloka-next]');
    nb && nb.addEventListener('click', () => { i = (i + 1) % SH.length; sk.animate && sk.animate([{ opacity: 0 }, { opacity: 1 }], { duration: 500 }); show(); });
  }
})();

window.showToast = function (msg) {
  let t = document.querySelector('.toast');
  if (!t) { t = document.createElement('div'); t.className = 'toast'; t.setAttribute('role', 'status'); document.body.appendChild(t); }
  t.textContent = msg; t.classList.add('show');
  clearTimeout(window.__tt); window.__tt = setTimeout(() => t.classList.remove('show'), 3400);
};

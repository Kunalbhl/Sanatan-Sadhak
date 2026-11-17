// Living background: random divine symbols and mantras that drift, glow in
// saffron or maroon, and react to the pointer. Symbols come from divine-data.js.
(function () {
  const bg = document.querySelector('.bg-global');
  const M = window.SadhakMotifs;
  if (!bg || !M) return;
  const reduce = window.matchMedia && matchMedia('(prefers-reduced-motion: reduce)').matches;
  const small = window.innerWidth < 760;
  const MANTRAS = ['ॐ', 'ॐ नमः शिवाय', 'जय श्री राम', 'हर हर महादेव', 'ॐ नमो नारायणाय', 'राधे राधे', 'जय महाकाल', 'श्री गणेशाय नमः', 'जय माता दी', 'हरे कृष्ण', 'ॐ नमो भगवते वासुदेवाय', 'जय हनुमान', 'शिवोऽहम्', 'श्री राम जय राम', 'ॐ शान्तिः', '卐'];
  const COLORS = ['#f28c00', '#e65100', '#7a1c1c', '#a8281e', '#f5b301'];
  const keys = Object.keys(M);
  const rnd = (a, b) => a + Math.random() * (b - a);
  const pick = (arr) => arr[Math.floor(Math.random() * arr.length)];
  const count = small ? 7 : 16;
  const items = [];
  let W = innerWidth, H = innerHeight, mx = -9999, my = -9999;

  function fill(it) {
    // every item is a random symbol or a random mantra, in a random theme colour
    const isText = Math.random() < 0.4;
    it.size = isText ? rnd(18, small ? 26 : 38) : rnd(small ? 70 : 90, small ? 130 : 210);
    it.el.className = 'dv' + (isText ? ' dv-text' : '');
    it.el.innerHTML = isText ? pick(MANTRAS) : M[pick(keys)];
    it.el.style.width = isText ? 'auto' : it.size + 'px';
    it.el.style.height = isText ? 'auto' : it.size + 'px';
    it.el.style.fontSize = isText ? it.size + 'px' : '';
    it.el.style.color = pick(COLORS);
    it.base = rnd(0.10, 0.2);
  }
  function spawn(it, anywhere) {
    fill(it);
    it.x = rnd(0, W); it.y = anywhere ? rnd(0, H) : H + it.size;
    it.vx = rnd(-0.12, 0.12); it.vy = rnd(-0.28, -0.08);
    it.rot = rnd(-12, 12); it.vr = rnd(-0.03, 0.03);
    it.px = 0; it.py = 0; it.glow = 0;
  }
  for (let i = 0; i < count; i++) {
    const el = document.createElement('div');
    bg.appendChild(el);
    const it = { el };
    spawn(it, true);
    items.push(it);
  }
  // each one re-tints itself now and then (saffron ↔ maroon)
  setInterval(() => { const it = pick(items); it.el.style.color = pick(COLORS); }, 1800);

  function draw() {
    for (const it of items) {
      const cx = it.x + it.px, cy = it.y + it.py;
      const dx = cx - mx, dy = cy - my, d = Math.hypot(dx, dy);
      const near = d < 220 ? 1 - d / 220 : 0;
      // pointer pushes symbols away and makes them glow
      it.px += (near * (dx / (d || 1)) * 60 - it.px) * 0.06;
      it.py += (near * (dy / (d || 1)) * 60 - it.py) * 0.06;
      it.glow += (near - it.glow) * 0.08;
      if (!reduce) { it.x += it.vx; it.y += it.vy; it.rot += it.vr; }
      if (it.y < -it.size * 1.5 || it.x < -260 || it.x > W + 260) spawn(it, false);
      const s = 1 + it.glow * 0.25;
      it.el.style.transform = `translate(${(it.x + it.px).toFixed(1)}px,${(it.y + it.py).toFixed(1)}px) translate(-50%,-50%) rotate(${it.rot.toFixed(2)}deg) scale(${s.toFixed(3)})`;
      it.el.style.opacity = (it.base + it.glow * 0.6).toFixed(3);
      it.el.style.filter = `drop-shadow(0 0 ${(6 + it.glow * 18).toFixed(0)}px currentColor)`;
    }
    if (!reduce || mx > -999) requestAnimationFrame(draw);
  }
  addEventListener('pointermove', (e) => { mx = e.clientX; my = e.clientY; }, { passive: true });
  addEventListener('pointerleave', () => { mx = my = -9999; });
  addEventListener('resize', () => { W = innerWidth; H = innerHeight; });
  // tap or click: a ripple of glow through nearby symbols
  addEventListener('pointerdown', (e) => { for (const it of items) { if (Math.hypot(it.x - e.clientX, it.y - e.clientY) < 320) { it.glow = 1; it.el.style.color = pick(COLORS); } } }, { passive: true });
  draw();

  // Scroll-to-top button
  const up = document.createElement('button');
  up.className = 'to-top'; up.type = 'button'; up.setAttribute('aria-label', 'Scroll to top'); up.textContent = '↑';
  document.body.appendChild(up);
  up.addEventListener('click', () => scrollTo({ top: 0, behavior: reduce ? 'auto' : 'smooth' }));
  const onScroll = () => up.classList.toggle('show', scrollY > 500);
  addEventListener('scroll', onScroll, { passive: true }); onScroll();
})();

// Hero banner: the owner's temple artwork (img/temple-hero.webp) brought to life.
// The picture is colour-graded to the visitor's local time (Brahma Muhurta, morning,
// afternoon, sandhya, night with stars and the real moon phase). On top: a waving
// saffron dhwaja on the main shikhara's pole, flying birds, a Hanuman langur on the
// shikhara, drifting golden dust, and the greeting / countdown overlay.
// Motion is driven by JavaScript so it keeps moving with OS animations turned off.
(function () {
  const host = document.querySelector('[data-scene]');
  if (!host || !window.SadhakAstro) return;
  const A = window.SadhakAstro;
  const W = 1000, H = 477;          // same aspect as the artwork (2372 × 1132)
  const POLE = { x: 839.6, y: 22 }; // top of the flag pole in the artwork
  const f = (n) => n.toFixed(1);
  const NS = 'http://www.w3.org/2000/svg';

  function stars() {
    let s = '';
    for (let i = 0; i < 60; i++) s += `<circle class="st" cx="${f((i * 157.3) % W)}" cy="${f((i * 53.7) % 230)}" r="${i % 5 === 0 ? 1.6 : 1}" fill="#fff"/>`;
    return s;
  }

  host.insertAdjacentHTML('afterbegin', `
  <picture class="scene-img">
    <source media="(max-width: 760px)" srcset="/img/temple-hero-1200.webp">
    <img src="/img/temple-hero.webp" alt="Temple shikharas glowing at sunrise" width="2372" height="1132" fetchpriority="high">
  </picture>
  <div class="scene-grade" data-grade></div>
  <svg class="scene-fx" viewBox="0 0 ${W} ${H}" preserveAspectRatio="${innerWidth <= 760 ? 'xMaxYMid' : 'xMidYMid'} slice" aria-hidden="true">
    <defs>
      <linearGradient id="flagG" x1="0" x2="1"><stop offset="0" stop-color="#ff8a00"/><stop offset="1" stop-color="#e64a19"/></linearGradient>
      <radialGradient id="fur" cx=".4" cy=".35"><stop offset="0" stop-color="#e6e4dc"/><stop offset=".7" stop-color="#b9b7ad"/><stop offset="1" stop-color="#8f8d84"/></radialGradient>
      <radialGradient id="moonGlow"><stop offset="0" stop-color="#fffbe8" stop-opacity=".5"/><stop offset="1" stop-color="#fffbe8" stop-opacity="0"/></radialGradient>
      <radialGradient id="lampG"><stop offset="0" stop-color="#ffd27a" stop-opacity=".9"/><stop offset="1" stop-color="#ffb300" stop-opacity="0"/></radialGradient>
    </defs>
    <g id="starsG" opacity="0">${stars()}</g>
    <g id="moonG" opacity="0" transform="translate(250,80)"><circle r="60" fill="url(#moonGlow)"/><circle r="18" fill="#fdf6dc"/><path id="moonShade" fill="#1b2350" opacity=".9"/></g>
    <g id="lampsG" opacity="0">
      <circle cx="839" cy="295" r="26" fill="url(#lampG)"/><circle cx="866" cy="415" r="18" fill="url(#lampG)"/><circle cx="740" cy="330" r="16" fill="url(#lampG)"/>
    </g>
    <path id="scFlag" fill="url(#flagG)" stroke="#b33a00" stroke-width=".6"/>
    <g transform="translate(796,226) scale(.62)">
      <path id="scTail" fill="none" stroke="#8d8d86" stroke-width="4.2" stroke-linecap="round"/>
      <path d="M-4,28 C-10,10 -6,-6 6,-10 C18,-14 28,-4 28,12 C28,24 22,32 8,32 Z" fill="url(#fur)"/>
      <path d="M2,30 C-6,30 -12,36 -8,40 L10,40 Z" fill="#b8b6ac"/><path d="M18,30 C26,30 32,36 28,40 L12,40 Z" fill="#b8b6ac"/>
      <path d="M22,6 C30,12 30,22 26,30" stroke="#a9a79d" stroke-width="4.5" fill="none" stroke-linecap="round"/>
      <g id="scHead">
        <circle cx="12" cy="-16" r="11.5" fill="url(#fur)"/>
        <ellipse cx="12" cy="-13" rx="7.2" ry="7.8" fill="#1d1a1a"/>
        <circle cx="9.2" cy="-15" r="1.3" fill="#e9c46a"/><circle cx="14.8" cy="-15" r="1.3" fill="#e9c46a"/>
        <circle cx="1.5" cy="-15" r="2.4" fill="#1d1a1a"/><circle cx="22.5" cy="-15" r="2.4" fill="#1d1a1a"/>
      </g>
    </g>
    <g id="birdsG"></g>
    <g id="dustG"></g>
  </svg>`);

  host.insertAdjacentHTML('beforeend', `
  <div class="scene-over">
    <div class="so-title" data-scene-greet>Welcome Devotee</div>
    <div class="so-chant"><span>✦</span> ॥ JAI SHREE RAM ॥ <span>✦</span></div>
    <div class="so-date" data-scene-date></div>
    <div class="so-chip"><span class="deva" data-scene-names>श्री राम • रामचंद्र • रघुनाथ • राघव</span><span class="so-sep"></span><span data-scene-next>⏱</span></div>
  </div>`);
  host.insertAdjacentHTML('afterend', `
  <div class="scene-bar" role="group" aria-label="Preview time of day">
    <button type="button" class="ph" data-t="auto" aria-pressed="true"><b>Live</b><small data-scene-label>—</small></button>
    <button type="button" class="ph" data-t="brahma"><b>Brahma Muhurta</b><small data-ph="brahma"></small></button>
    <button type="button" class="ph" data-t="dawn"><b>Morning</b><small data-ph="dawn"></small></button>
    <button type="button" class="ph" data-t="noon"><b>Afternoon</b><small data-ph="noon"></small></button>
    <button type="button" class="ph" data-t="dusk"><b>Sandhya</b><small data-ph="dusk"></small></button>
    <button type="button" class="ph" data-t="night"><b>Night</b><small data-ph="night"></small></button>
  </div>`);

  const $ = (id) => document.getElementById(id);
  const img = host.querySelector('.scene-img img');
  const grade = host.querySelector('[data-grade]');

  const birds = [];
  [[70, 22, 3], [120, 30, 2], [45, 36, 2], [160, 26, 4], [95, 44, 1]].forEach(([y, dur, n], fi) => {
    for (let j = 0; j < n; j++) {
      const p = document.createElementNS(NS, 'path');
      p.setAttribute('fill', '#3a2418');
      $('birdsG').appendChild(p);
      birds.push({ el: p, y: y + j * 8 + (j % 2) * 5, dx: j * 22 + (j % 2) * 6, dur, start: -fi * 5.1, phase: Math.random() * 6, scale: 0.8 + (fi % 3) * 0.2 });
    }
  });
  function birdPath(x, y, s, w) {
    const tip = -9 * w * s, mid = -4 * w * s;
    return `M${f(x)},${f(y)} c${f(4 * s)},${f(-s)} ${f(7 * s)},${f(-s)} ${f(11 * s)},0 c${f(-3 * s)},${f(1.5 * s)} ${f(-8 * s)},${f(1.5 * s)} ${f(-11 * s)},0 Z` +
      ` M${f(x + 5 * s)},${f(y)} Q${f(x + s)},${f(y + mid)} ${f(x - 9 * s)},${f(y + tip)} Q${f(x - s)},${f(y + mid * .4 + s)} ${f(x + 5 * s)},${f(y + 1.6 * s)} Z` +
      ` M${f(x + 6 * s)},${f(y)} Q${f(x + 9 * s)},${f(y + mid)} ${f(x + 18 * s)},${f(y + tip)} Q${f(x + 10 * s)},${f(y + mid * .4 + s)} ${f(x + 6 * s)},${f(y + 1.6 * s)} Z` +
      ` M${f(x + 11 * s)},${f(y)} l${f(3 * s)},${f(-1.4 * s)} l${f(-s)},${f(2.4 * s)} Z`;
  }
  const dust = [];
  for (let i = 0; i < 26; i++) { const c = document.createElementNS(NS, 'circle'); c.setAttribute('fill', '#ffe08a'); $('dustG').appendChild(c); dust.push({ el: c, x: Math.random() * W, y: 120 + Math.random() * 340, r: .7 + Math.random() * 1.6, v: .1 + Math.random() * .3, p: Math.random() * 6 }); }

  const GRADES = {
    brahma: { filter: 'brightness(.55) saturate(.7) hue-rotate(-12deg) contrast(1.05)', wash: 'linear-gradient(180deg, rgba(40,50,120,.65), rgba(70,50,110,.35) 60%, rgba(30,20,50,.5))', blend: 'multiply' },
    dawn: { filter: 'brightness(1.02) saturate(1.05)', wash: 'linear-gradient(180deg, rgba(255,220,170,.10), rgba(255,190,120,.05))', blend: 'soft-light' },
    noon: { filter: 'brightness(1.1) saturate(.82) contrast(1.02)', wash: 'linear-gradient(180deg, rgba(150,200,255,.35), rgba(255,240,220,.05) 60%)', blend: 'soft-light' },
    dusk: { filter: 'brightness(.92) saturate(1.35) hue-rotate(-8deg) contrast(1.08)', wash: 'linear-gradient(180deg, rgba(120,60,160,.30), rgba(255,90,40,.25) 55%, rgba(80,20,10,.35))', blend: 'multiply' },
    night: { filter: 'brightness(.42) saturate(.55) contrast(1.1)', wash: 'linear-gradient(180deg, rgba(10,20,70,.85), rgba(25,30,90,.55) 60%, rgba(10,10,30,.6))', blend: 'multiply' }
  };
  let forced = null, isNight = false, dustOn = true;

  function sky() {
    const now = new Date();
    const tz = -now.getTimezoneOffset() / 60;
    const st = A.sunTimes(now, 26.79, tz * 15, tz);
    const PH = { brahma: st.sunrise - 96, dawn: st.sunrise + 80, noon: (st.sunrise + st.sunset) / 2 + 40, dusk: st.sunset - 6, night: (st.sunset + 220) % 1440 };
    Object.entries(PH).forEach(([k, v]) => { const e = document.querySelector(`[data-ph="${k}"]`); if (e) e.textContent = A.fmtMin(v); });
    let mins = now.getHours() * 60 + now.getMinutes();
    if (forced) mins = PH[forced];
    const m = ((mins % 1440) + 1440) % 1440;
    const key = (m >= st.sunrise - 100 && m < st.sunrise - 20) ? 'brahma'
      : (m >= st.sunrise - 20 && m < st.sunrise + 150) ? 'dawn'
      : (m >= st.sunrise + 150 && m < st.sunset - 60) ? 'noon'
      : (m >= st.sunset - 60 && m < st.sunset + 40) ? 'dusk' : 'night';
    const g = GRADES[key];
    img.style.filter = g.filter;
    grade.style.background = g.wash;
    grade.style.mixBlendMode = g.blend;
    isNight = key === 'night' || key === 'brahma';
    dustOn = !isNight;
    $('starsG').setAttribute('opacity', isNight ? (key === 'night' ? .95 : .6) : 0);
    $('moonG').setAttribute('opacity', isNight ? 1 : 0);
    $('lampsG').setAttribute('opacity', isNight ? 1 : key === 'dusk' ? .6 : 0);
    const lm = A.limbs(A.jdFromDate(now));
    const k = Math.cos(lm.elong * Math.PI / 180), r = 18, waxing = lm.elong < 180;
    $('moonShade').setAttribute('d', `M0,${-r} A${r},${r} 0 0 ${waxing ? 0 : 1} 0,${r} A${f(Math.abs(k) * r)},${r} 0 0 ${(k > 0) === waxing ? 1 : 0} 0,${-r} Z`);
    birds.forEach((b) => b.el.setAttribute('fill', isNight ? '#0c0f24' : '#3a2418'));

    const hi = document.documentElement.lang === 'hi';
    const NAMES = { brahma: ['Brahma Muhurta', 'ब्रह्म मुहूर्त'], dawn: ['Morning', 'प्रातः'], noon: ['Afternoon', 'अपराह्न'], dusk: ['Sandhya', 'सन्ध्या'], night: ['Night', 'रात्रि'] };
    document.querySelector('[data-scene-label]').textContent = `${NAMES[key][hi ? 1 : 0]} · ${A.fmtMin(m)}`;
    const GREET = { brahma: ['Shubh Brahma Muhurta', 'शुभ ब्रह्म मुहूर्त'], dawn: ['Suprabhatam, Devotee', 'सुप्रभातम्, साधक'], noon: ['Welcome Devotee', 'स्वागतम् साधक'], dusk: ['Shubh Sandhya', 'शुभ सन्ध्या'], night: ['Shubh Ratri', 'शुभ रात्रि'] };
    document.querySelector('[data-scene-greet]').textContent = GREET[key][hi ? 1 : 0];
    document.querySelector('[data-scene-date]').textContent = now.toLocaleDateString(hi ? 'hi-IN' : 'en-IN', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric' }) + ' • ' + A.fmtMin(m);
    const nowM = now.getHours() * 60 + now.getMinutes();
    const nexts = [[st.sunrise - 96, hi ? 'ब्रह्म मुहूर्त' : 'Brahma Muhurta'], [st.sunrise, hi ? 'प्रातः सन्ध्या' : 'Pratah Sandhya'], [st.noon, hi ? 'मध्याह्न' : 'Madhyahna'], [st.sunset, hi ? 'सायं सन्ध्या' : 'Sayam Sandhya']];
    const nx = nexts.find(([t]) => t > nowM) || [nexts[0][0] + 1440, nexts[0][1]];
    const dm = Math.round(nx[0] - nowM);
    document.querySelector('[data-scene-next]').textContent = `⏱ ${nx[1]} ${hi ? 'में' : 'in'} ${String(Math.floor(dm / 60)).padStart(2, '0')}:${String(dm % 60).padStart(2, '0')}`;
  }

  const RN = ['श्री राम • रामचंद्र • रघुनाथ • राघव', 'जानकीवल्लभ • दशरथनंदन • कौसल्यानंदन', 'सीताराम • रघुपति राघव राजा राम', 'मर्यादा पुरुषोत्तम • कोदंडधारी'];
  let ni = 0;
  setInterval(() => { ni = (ni + 1) % RN.length; const e = document.querySelector('[data-scene-names]'); e.style.opacity = 0; setTimeout(() => { e.textContent = RN[ni]; e.style.opacity = 1; }, 400); }, 4000);

  const flag = $('scFlag'), tail = $('scTail'), head = $('scHead');
  const starEls = [...document.querySelectorAll('#starsG .st')];
  let visible = true, headR = 0;
  if ('IntersectionObserver' in window) new IntersectionObserver((e) => { visible = e[0].isIntersecting; }).observe(host);

  function frame(ms) {
    const t = ms / 1000;
    if (visible) {
      const fx = POLE.x + .6, fy = POLE.y, len = 64, hgt = 30;
      let top = `M${f(fx)},${f(fy)}`, bot = '';
      for (let i = 1; i <= 12; i++) {
        const x = (i / 12) * len, wv = Math.sin(t * 5 - x / 9) * (x / len) * 5;
        top += ` L${f(fx + x)},${f(fy + (x / len) * 6 + wv)}`;
        bot = ` L${f(fx + x)},${f(fy + hgt - (x / len) * 8 + wv)}` + bot;
      }
      const ew = Math.sin(t * 5 - len / 9) * 5;
      flag.setAttribute('d', `${top} L${f(fx + len - 16)},${f(fy + hgt / 2 + ew)}${bot} L${f(fx)},${f(fy + hgt)} Z`);
      const sw = Math.sin(t * 1.3) * 10;
      tail.setAttribute('d', `M0,26 C-18,${f(34 + sw * .3)} ${f(-34 + sw)},${f(18 + sw * .4)} ${f(-30 + sw)},${f(-4 + sw * .2)} C${f(-28 + sw)},-16 ${f(-18 + sw * .6)},-18 ${f(-16 + sw * .5)},-10`);
      const s45 = Math.sin(t * .45), look = s45 > .55 ? -16 : s45 < -.7 ? 10 : 0;
      headR += (look - headR) * .05;
      head.setAttribute('transform', `rotate(${headR.toFixed(2)} 12 -6)`);
      for (const b of birds) {
        const p = (((t - b.start) / b.dur) % 1 + 1) % 1;
        b.el.setAttribute('d', birdPath(-80 + p * 1180 + b.dx, b.y - p * 30 + Math.sin(t * 1.2 + b.phase) * 3, b.scale, Math.sin(t * 9 + b.phase)));
      }
      for (const d of dust) {
        d.y -= d.v * .35; d.x += Math.sin(t * .5 + d.p) * .2; if (d.y < 100) { d.y = 470; d.x = Math.random() * W; }
        d.el.setAttribute('cx', f(d.x)); d.el.setAttribute('cy', f(d.y)); d.el.setAttribute('r', d.r);
        d.el.setAttribute('opacity', dustOn ? (.25 + .5 * Math.abs(Math.sin(t + d.p))).toFixed(2) : 0);
      }
      if (isNight) starEls.forEach((s, i) => s.setAttribute('opacity', (.4 + .6 * Math.abs(Math.sin(t * .8 + i))).toFixed(2)));
    }
    requestAnimationFrame(frame);
  }

  const bar = host.nextElementSibling;
  bar.querySelectorAll('button').forEach((b) => b.addEventListener('click', () => {
    forced = b.dataset.t === 'auto' ? null : b.dataset.t;
    bar.querySelectorAll('button').forEach((x) => x.setAttribute('aria-pressed', String(x === b)));
    sky();
  }));
  sky();
  setInterval(sky, 20000);
  document.addEventListener('langchange', sky);
  requestAnimationFrame(frame);
})();

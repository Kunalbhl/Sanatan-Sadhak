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
  const W = 1672, H = 941;          // same size as the artwork
  const POLE = { x: 1243.5, y: 64 }; // where the flag meets the pole
  const f = (n) => n.toFixed(1);
  const NS = 'http://www.w3.org/2000/svg';

  function stars() {
    let s = '';
    for (let i = 0; i < 60; i++) s += `<circle class="st" cx="${f((i * 157.3) % W)}" cy="${f((i * 53.7) % 420)}" r="${i % 5 === 0 ? 2.4 : 1.5}" fill="#fff"/>`;
    return s;
  }

  host.insertAdjacentHTML('afterbegin', `
  <picture class="scene-img">
    <source media="(max-width: 760px)" srcset="/img/temple-hero-1200.webp">
    <img src="/img/temple-hero.webp" alt="Temple shikharas glowing at sunrise" width="1672" height="941" fetchpriority="high">
  </picture>
  <svg class="scene-fx" viewBox="0 0 ${W} ${H}" preserveAspectRatio="${innerWidth <= 760 ? 'xMaxYMid' : 'xMidYMid'} slice" aria-hidden="true">
    <defs>
      <linearGradient id="flagG" x1="0" x2="1"><stop offset="0" stop-color="#ff8a00"/><stop offset="1" stop-color="#e64a19"/></linearGradient>
      <radialGradient id="fur" cx=".4" cy=".35"><stop offset="0" stop-color="#e6e4dc"/><stop offset=".7" stop-color="#b9b7ad"/><stop offset="1" stop-color="#8f8d84"/></radialGradient>
      <radialGradient id="moonGlow"><stop offset="0" stop-color="#fffbe8" stop-opacity=".5"/><stop offset="1" stop-color="#fffbe8" stop-opacity="0"/></radialGradient>
      <radialGradient id="sunGlow"><stop offset="0" stop-color="#fffbe6"/><stop offset=".25" stop-color="#ffd54f" stop-opacity=".9"/><stop offset="1" stop-color="#ff9800" stop-opacity="0"/></radialGradient>
      <radialGradient id="lampG"><stop offset="0" stop-color="#ffd27a" stop-opacity=".9"/><stop offset="1" stop-color="#ffb300" stop-opacity="0"/></radialGradient>
    </defs>
    <g id="starsG" opacity="0">${stars()}</g>
    <g id="sunG" opacity="0"><circle r="150" fill="url(#sunGlow)"/><circle r="40" fill="#fff6d0"/></g>
    <g id="moonG" opacity="0"><circle r="90" fill="url(#moonGlow)"/><circle r="26" fill="#fdf6dc"/><path id="moonShade" fill="#1b2350" opacity=".9"/></g>
  </svg>
  <picture class="scene-img scene-fg">
    <source media="(max-width: 760px)" srcset="/img/temple-fg-960.webp">
    <img src="/img/temple-fg.webp" alt="" width="1672" height="941">
  </picture>
  <div class="scene-grade" data-grade></div>
  <svg class="scene-fx" viewBox="0 0 ${W} ${H}" preserveAspectRatio="${innerWidth <= 760 ? 'xMaxYMid' : 'xMidYMid'} slice" aria-hidden="true">
    <g id="lampsG" opacity="0">
      <circle cx="1218" cy="790" r="40" fill="url(#lampG)"/><circle cx="1016" cy="785" r="34" fill="url(#lampG)"/><circle cx="1430" cy="785" r="34" fill="url(#lampG)"/><circle cx="700" cy="800" r="24" fill="url(#lampG)"/><circle cx="1500" cy="815" r="22" fill="url(#lampG)"/>
    </g>
    <path id="scFlag" fill="url(#flagG)" stroke="#b33a00" stroke-width=".6"/>
    <!-- Rhesus macaque (Indian temple monkey): brown fur, red face; feet at (0,0), facing right -->
    <g id="mk"><g id="mkBody">
      <path id="mkTail" fill="none" stroke="#7a5532" stroke-width="4" stroke-linecap="round"/>
      <path id="mkLegB" d="M-6,-14 C-12,-6 -10,-2 -4,0" stroke="#6e4b2c" stroke-width="6" fill="none" stroke-linecap="round"/>
      <ellipse cx="0" cy="-24" rx="15" ry="17" fill="#8b6542"/>
      <ellipse cx="4" cy="-21" rx="8" ry="11" fill="#c9a074"/>
      <path id="mkLegF" d="M6,-14 C12,-6 10,-2 4,0" stroke="#7d5838" stroke-width="6" fill="none" stroke-linecap="round"/>
      <g id="mkArm"><path d="M8,-32 C16,-26 18,-18 14,-12" stroke="#7d5838" stroke-width="5.5" fill="none" stroke-linecap="round"/></g>
      <g id="mkHead">
        <circle cx="6" cy="-46" r="12" fill="#8b6542"/>
        <circle cx="-4" cy="-48" r="3.6" fill="#d77a6f"/>
        <path d="M2,-53 C6,-58 14,-56 16,-50 C18,-44 15,-38 9,-37 C4,-36 0,-40 0,-45 Z" fill="#d9665c"/>
        <ellipse cx="12" cy="-42" rx="4" ry="3" fill="#c9534a"/>
        <circle cx="7" cy="-48" r="1.5" fill="#1a0d08"/><circle cx="13" cy="-48" r="1.5" fill="#1a0d08"/>
        <path d="M9,-40 Q11.5,-38.5 14,-40" stroke="#6b1f18" stroke-width="1" fill="none"/>
        <path d="M-4,-56 C2,-62 12,-60 16,-55" stroke="#a27a52" stroke-width="2.4" fill="none"/>
      </g>
    </g></g>
    <g id="birdsG"></g>
    <g id="dustG"></g>
  </svg>`);

  host.insertAdjacentHTML('beforeend', `
  <div class="scene-over">
    <div class="so-title" data-scene-greet>Welcome Devotee</div>
    <div class="so-chant"><span>✦</span> ॥ JAI SHREE RAM ॥ <span>✦</span></div>
    <div class="so-date" data-scene-date></div>
    <div class="so-chip"><span data-scene-names>श्री राम • रामचंद्र • रघुनाथ • राघव</span><span class="so-sep"></span><span data-scene-next>⏱</span></div>
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
  const imgs = host.querySelectorAll('.scene-img img');
  const grade = host.querySelector('[data-grade]');

  const birds = [];
  [[180, 26, 4], [300, 34, 3], [120, 40, 2], [400, 30, 5], [240, 48, 2]].forEach(([y, dur, n], fi) => {
    for (let j = 0; j < n; j++) {
      const p = document.createElementNS(NS, 'path');
      p.setAttribute('fill', '#3a2418');
      $('birdsG').appendChild(p);
      birds.push({ el: p, y: y + j * 16 + (j % 2) * 10, dx: j * 46 + (j % 2) * 14, dur, start: -fi * 5.1, phase: Math.random() * 6, scale: 1.6 + (fi % 3) * 0.4 });
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
  for (let i = 0; i < 26; i++) { const c = document.createElementNS(NS, 'circle'); c.setAttribute('fill', '#ffe08a'); $('dustG').appendChild(c); dust.push({ el: c, x: Math.random() * W, y: 300 + Math.random() * 600, r: 1.2 + Math.random() * 2.4, v: .1 + Math.random() * .3, p: Math.random() * 6 }); }

  const GRADES = {
    brahma: { filter: 'brightness(.62) saturate(.7) hue-rotate(-18deg) contrast(1.05)', wash: 'linear-gradient(180deg, rgba(30,40,120,.75), rgba(70,50,120,.45) 60%, rgba(20,15,45,.55))', blend: 'multiply' },
    dawn: { filter: 'brightness(1.03) saturate(.95) hue-rotate(6deg)', wash: 'linear-gradient(180deg, rgba(170,190,255,.25), rgba(255,200,150,.10) 60%)', blend: 'soft-light' },
    noon: { filter: 'brightness(1.12) saturate(.7) contrast(1.02)', wash: 'linear-gradient(180deg, rgba(120,180,255,.55), rgba(200,225,255,.25) 50%, rgba(255,240,220,0) 75%)', blend: 'soft-light' },
    dusk: { filter: 'saturate(1.1)', wash: 'none', blend: 'normal' },
    night: { filter: 'brightness(.5) saturate(.55) contrast(1.1)', wash: 'linear-gradient(180deg, rgba(8,18,70,.9), rgba(25,30,90,.6) 60%, rgba(10,10,30,.6))', blend: 'multiply' }
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
    imgs.forEach((i) => { i.style.filter = g.filter; });
    grade.style.background = g.wash;
    grade.style.mixBlendMode = g.blend;
    isNight = key === 'night' || key === 'brahma';
    dustOn = !isNight;
    $('starsG').setAttribute('opacity', isNight ? (key === 'night' ? .95 : .6) : 0);
    // Sun along today's arc: rises bottom-left, highest at noon, sets bottom-right
    const fr = (m - st.sunrise) / (st.sunset - st.sunrise);
    const alt = Math.sin(Math.PI * Math.min(Math.max(fr, 0), 1));
    $('sunG').setAttribute('transform', `translate(${f(140 + fr * 1400)},${f(720 - alt * 600)})`);
    $('sunG').setAttribute('opacity', fr > -0.03 && fr < 1.03 ? 1 : 0);
    let nf = (m - st.sunset) / (1440 - (st.sunset - st.sunrise)); if (nf < 0) nf += 1440 / (1440 - (st.sunset - st.sunrise)); nf %= 1;
    $('moonG').setAttribute('transform', `translate(${f(160 + nf * 1350)},${f(680 - Math.sin(Math.PI * nf) * 560)})`);
    $('moonG').setAttribute('opacity', isNight ? 1 : 0);
    $('lampsG').setAttribute('opacity', isNight ? 1 : key === 'dusk' ? .6 : 0);
    const lm = A.limbs(A.jdFromDate(now));
    const k = Math.cos(lm.elong * Math.PI / 180), r = 26, waxing = lm.elong < 180;
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

  const RN0 = 0; const RN = ['श्री राम • रामचंद्र • रघुनाथ • राघव', 'जानकीवल्लभ • दशरथनंदन • कौसल्यानंदन', 'सीताराम • रघुपति राघव राजा राम', 'मर्यादा पुरुषोत्तम • कोदंडधारी'];
  let ni = 0;
  setInterval(() => { ni = (ni + 1) % RN.length; const e = document.querySelector('[data-scene-names]'); e.style.opacity = 0; setTimeout(() => { e.textContent = RN[ni]; e.style.opacity = 1; }, 400); }, 4000);

  const flag = $('scFlag');
  // ---- Playful macaque: hops between temple roofs, then scratches, waves, peeks or bounces ----
  const mk = { el: $('mk'), body: $('mkBody'), tail: $('mkTail'), head: $('mkHead'), arm: $('mkArm'), legF: $('mkLegF'), legB: $('mkLegB') };
  const SPOTS = [[1130, 650], [1210, 612], [1330, 660], [1478, 668], [1400, 600], [1560, 700], [1265, 560]];
  const M = { at: 0, x: SPOTS[0][0], y: SPOTS[0][1], dir: 1, state: 'idle', act: 'sit', t0: 0, dur: 2.5, from: null, to: null, hop: 0 };
  const ACTS = ['sit', 'scratch', 'wave', 'peek', 'bounce', 'scratch', 'wave'];
  function nextMove(t) {
    if (M.state === 'idle') {
      // pick a neighbour roof (mostly close ones, sometimes a big leap)
      const cands = SPOTS.map((p, i) => [i, Math.hypot(p[0] - M.x, p[1] - M.y)]).filter(([i]) => i !== M.at).sort((a, b) => a[1] - b[1]);
      const pick = cands[Math.random() < .75 ? Math.floor(Math.random() * 2) : Math.floor(Math.random() * cands.length)][0];
      M.from = [M.x, M.y]; M.to = SPOTS[pick]; M.at = pick;
      const d = Math.hypot(M.to[0] - M.x, M.to[1] - M.y);
      M.dir = M.to[0] >= M.x ? 1 : -1; M.state = 'hop'; M.t0 = t; M.dur = .55 + d / 420; M.hop = 60 + d * .35;
    } else {
      M.state = 'idle'; M.t0 = t; M.dur = 1.6 + Math.random() * 2.6; M.act = ACTS[Math.floor(Math.random() * ACTS.length)];
      if (Math.random() < .35) M.dir *= -1; // turn around to look the other way
    }
  }
  function monkey(t) {
    const k = (t - M.t0) / M.dur;
    if (k >= 1) nextMove(t);
    const q = Math.min(1, Math.max(0, (t - M.t0) / M.dur));
    let bodyY = 0, bodyR = 0, armR = 0, headR = 0, legs = 0;
    if (M.state === 'hop') {
      const e = q < .5 ? 2 * q * q : 1 - Math.pow(-2 * q + 2, 2) / 2;
      M.x = M.from[0] + (M.to[0] - M.from[0]) * e;
      M.y = M.from[1] + (M.to[1] - M.from[1]) * e - Math.sin(Math.PI * q) * M.hop;
      bodyR = -18 + 36 * q; legs = Math.sin(Math.PI * q); armR = -60 * Math.sin(Math.PI * q);
    } else {
      if (M.act === 'sit') { bodyY = Math.sin(t * 3) * 1.2; headR = Math.sin(t * 1.4) * 10; }
      if (M.act === 'scratch') { armR = -120 + Math.sin(t * 22) * 12; headR = -12; bodyR = -4; }
      if (M.act === 'wave') { armR = -150 + Math.sin(t * 10) * 28; headR = 8 + Math.sin(t * 3) * 4; }
      if (M.act === 'peek') { bodyR = 14 * Math.sin(Math.min(1, q * 3) * Math.PI / 2); headR = 16; }
      if (M.act === 'bounce') { bodyY = -Math.abs(Math.sin(t * 8)) * 12; armR = -90; legs = Math.abs(Math.sin(t * 8)); }
    }
    mk.el.setAttribute('transform', `translate(${f(M.x)},${f(M.y + bodyY)}) scale(${M.dir * 2.1},2.1)`);
    mk.body.setAttribute('transform', `rotate(${bodyR.toFixed(1)} 0 -20)`);
    mk.head.setAttribute('transform', `rotate(${headR.toFixed(1)} 6 -36)`);
    mk.arm.setAttribute('transform', `rotate(${armR.toFixed(1)} 8 -32)`);
    const L = legs * 8;
    mk.legF.setAttribute('d', `M6,-14 C${f(12 + L)},-6 ${f(10 + L)},${f(-2 + L * .2)} ${f(4 + L * 1.4)},0`);
    mk.legB.setAttribute('d', `M-6,-14 C${f(-12 - L)},-6 ${f(-10 - L)},${f(-2 + L * .2)} ${f(-4 - L * 1.4)},0`);
    const sw = Math.sin(t * (M.state === 'hop' ? 6 : 1.6)) * 10;
    mk.tail.setAttribute('d', `M-12,-14 C-26,${f(-10 + sw * .3)} ${f(-36 + sw)},${f(-24 + sw * .4)} ${f(-32 + sw)},${f(-44 + sw * .2)} C${f(-30 + sw)},-54 ${f(-22 + sw * .6)},-56 ${f(-20 + sw * .5)},-50`);
  }
  const starEls = [...document.querySelectorAll('#starsG .st')];
  let visible = true;
  if ('IntersectionObserver' in window) new IntersectionObserver((e) => { visible = e[0].isIntersecting; }).observe(host);

  function frame(ms) {
    const t = ms / 1000;
    if (visible) {
      const fx = POLE.x + 2, fy = POLE.y, len = 250, hgt = 70;
      let top = `M${f(fx)},${f(fy)}`, bot = '';
      for (let i = 1; i <= 12; i++) {
        const x = (i / 12) * len, wv = Math.sin(t * 4 - x / 45) * (x / len) * 18;
        top += ` L${f(fx + x)},${f(fy + (x / len) * 60 + wv)}`;
        bot = ` L${f(fx + x)},${f(fy + hgt + (x / len) * 75 + wv)}` + bot;
      }
      const ew = Math.sin(t * 4 - len / 45) * 18;
      flag.setAttribute('d', `${top} L${f(fx + len - 45)},${f(fy + 100 + ew)}${bot} L${f(fx)},${f(fy + hgt)} Z`);
      monkey(t);
      for (const b of birds) {
        const p = (((t - b.start) / b.dur) % 1 + 1) % 1;
        b.el.setAttribute('d', birdPath(-120 + p * 1950 + b.dx, b.y - p * 60 + Math.sin(t * 1.2 + b.phase) * 3, b.scale, Math.sin(t * 9 + b.phase)));
      }
      for (const d of dust) {
        d.y -= d.v * .35; d.x += Math.sin(t * .5 + d.p) * .2; if (d.y < 250) { d.y = 930; d.x = Math.random() * W; }
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

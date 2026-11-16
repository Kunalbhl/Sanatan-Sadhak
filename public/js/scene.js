// Animated temple scene: a Nagara-style mandir (in the spirit of Ayodhya's Ram Mandir),
// sky following the visitor's local time, sun/moon on their arcs, birds, a monkey on
// the shikhar and a waving flag. Original line art built in code.
(function () {
  const host = document.querySelector('[data-scene]');
  if (!host || !window.SadhakAstro) return;
  const A = window.SadhakAstro;
  const NS = 'http://www.w3.org/2000/svg';
  const W = 1000, H = 690, GROUND = 600;

  // ---------- Temple drawing helpers ----------
  // Curvilinear Nagara shikhara between baseY and topY, centred at cx
  function shikhara(cx, baseY, topY, halfW, bands) {
    const h = baseY - topY;
    const pts = (side) => {
      const s = side;
      return `M${cx + s * halfW},${baseY} C${cx + s * halfW},${baseY - h * .45} ${cx + s * halfW * .78},${topY + h * .22} ${cx + s * halfW * .32},${topY}`;
    };
    let d = `${pts(-1)} L${cx + halfW * .32},${topY} C${cx + halfW * .78},${topY + h * .22} ${cx + halfW},${baseY - h * .45} ${cx + halfW},${baseY} Z`;
    let lines = '';
    for (let i = 1; i < bands; i++) {
      const t = i / bands, y = baseY - h * t;
      // approximate width along the curve
      const k = Math.pow(1 - t, .55);
      const hw = halfW * (.32 + .68 * k);
      lines += `<path d="M${cx - hw},${y} Q${cx},${y - 4} ${cx + hw},${y}" class="t-line"/>`;
    }
    // vertical ribs (urushringa feel)
    for (const f of [-.55, 0, .55]) {
      lines += `<path d="M${cx + f * halfW},${baseY} C${cx + f * halfW},${baseY - h * .5} ${cx + f * halfW * .6},${topY + h * .2} ${cx + f * halfW * .25},${topY + 6}" class="t-line"/>`;
    }
    return `<path d="${d}" class="t-body"/>${lines}`;
  }
  function amalaka(cx, y, r) {
    return `<ellipse cx="${cx}" cy="${y}" rx="${r}" ry="${r * .42}" class="t-body"/>
      ${[-.6, -.2, .2, .6].map((f) => `<path d="M${cx + f * r},${y - r * .38} L${cx + f * r},${y + r * .38}" class="t-line"/>`).join('')}`;
  }
  function kalash(cx, y, s) {
    return `<path d="M${cx - s * .6},${y} q${s * .6},${-s * 1.5} ${s * 1.2},0 z" class="t-gold"/>
      <circle cx="${cx}" cy="${y - s * 1.2}" r="${s * .35}" class="t-gold"/>
      <path d="M${cx},${y - s * 1.6} l0,${-s * .6}" stroke="#c8961a" stroke-width="2"/>`;
  }
  function dome(cx, baseY, r) {
    return `<path d="M${cx - r},${baseY} C${cx - r},${baseY - r * 1.25} ${cx + r},${baseY - r * 1.25} ${cx + r},${baseY} Z" class="t-body"/>
      <path d="M${cx - r * .55},${baseY} C${cx - r * .5},${baseY - r * .85} ${cx + r * .5},${baseY - r * .85} ${cx + r * .55},${baseY}" class="t-line"/>
      ${kalash(cx, baseY - r * .93, r * .16)}`;
  }
  function pillars(x1, x2, y1, y2, n) {
    let s = `<rect x="${x1}" y="${y1}" width="${x2 - x1}" height="${y2 - y1}" class="t-body"/>`;
    const step = (x2 - x1) / n;
    for (let i = 0; i <= n; i++) {
      const x = x1 + i * step;
      s += `<path d="M${x},${y1 + 6} L${x},${y2 - 4}" class="t-line"/>`;
      if (i < n) s += `<path d="M${x + 3},${y1 + 10} Q${x + step / 2},${y1 + 26} ${x + step - 3},${y1 + 10}" class="t-line"/>`;
    }
    s += `<path d="M${x1 - 6},${y1} L${x2 + 6},${y1}" class="t-edge"/><path d="M${x1 - 4},${y1 + 6} L${x2 + 4},${y1 + 6}" class="t-line"/>`;
    return s;
  }

  function templeSVG() {
    const cx = 500;
    let s = '';
    // plinth steps
    s += `<path d="M150,${GROUND} L850,${GROUND} L830,${GROUND - 14} L170,${GROUND - 14} Z" class="t-body"/>`;
    s += `<path d="M180,${GROUND - 14} L820,${GROUND - 14} L805,${GROUND - 30} L195,${GROUND - 30} Z" class="t-body"/>`;
    s += `<path d="M205,${GROUND - 30} L795,${GROUND - 30} L785,${GROUND - 44} L215,${GROUND - 44} Z" class="t-body"/>`;
    // pillared halls (left wing, centre, right wing)
    s += pillars(220, 360, GROUND - 120, GROUND - 44, 7);
    s += pillars(640, 780, GROUND - 120, GROUND - 44, 7);
    s += pillars(360, 640, GROUND - 150, GROUND - 44, 10);
    // upper storey of halls
    s += pillars(240, 350, GROUND - 168, GROUND - 120, 5);
    s += pillars(650, 760, GROUND - 168, GROUND - 120, 5);
    // mandap domes
    s += dome(295, GROUND - 168, 46);
    s += dome(705, GROUND - 168, 46);
    s += dome(400, GROUND - 150, 40);
    s += dome(600, GROUND - 150, 40);
    // small corner shikharas
    s += shikhara(232, GROUND - 120, GROUND - 200, 16, 5) + amalaka(232, GROUND - 202, 9) + kalash(232, GROUND - 206, 5);
    s += shikhara(768, GROUND - 120, GROUND - 200, 16, 5) + amalaka(768, GROUND - 202, 9) + kalash(768, GROUND - 206, 5);
    // garbhagriha base
    s += pillars(430, 570, GROUND - 230, GROUND - 150, 6);
    // main shikhara with clustered sub-towers
    s += shikhara(cx - 64, GROUND - 230, GROUND - 330, 26, 6) + shikhara(cx + 64, GROUND - 230, GROUND - 330, 26, 6);
    s += shikhara(cx, GROUND - 230, 150, 78, 12);
    s += amalaka(cx, 146, 30);
    s += kalash(cx, 132, 13);
    // flag pole + flag (waving)
    s += `<path d="M${cx},112 L${cx},40" stroke="#8a5a1a" stroke-width="3" stroke-linecap="round"/>`;
    s += `<g transform="translate(${cx + 1},44)"><path class="sc-flag" d="M0,0 L74,10 L50,20 L74,30 L0,38 Z" fill="url(#flagG)" stroke="#c43e00" stroke-width="1"/></g>`;
    // monkey sitting on the shikhar ledge (left of amalaka)
    s += `<g transform="translate(${cx - 56},176) scale(1.55)">
      <path class="sc-tail" d="M6,10 C-10,22 -26,10 -22,-6 C-20,-14 -12,-14 -10,-8" fill="none" stroke="#6b4426" stroke-width="3.2" stroke-linecap="round"/>
      <ellipse cx="10" cy="4" rx="11" ry="13" fill="#7a4e2c"/>
      <ellipse cx="10" cy="7" rx="6" ry="8" fill="#c99467"/>
      <g class="sc-monkey-head"><circle cx="11" cy="-14" r="9" fill="#7a4e2c"/><ellipse cx="13" cy="-12" rx="6" ry="5" fill="#d9a77a"/>
        <circle cx="11" cy="-14" r="1.3" fill="#2a160a"/><circle cx="16" cy="-14" r="1.3" fill="#2a160a"/>
        <circle cx="2.5" cy="-15" r="3" fill="#7a4e2c"/><circle cx="2.5" cy="-15" r="1.6" fill="#d9a77a"/></g>
      <path d="M2,14 q-4,8 4,9 M18,14 q4,8 -4,9" stroke="#6b4426" stroke-width="3" fill="none" stroke-linecap="round"/>
    </g>`;
    // diyas along the plinth
    for (let x = 230; x <= 770; x += 54) {
      s += `<g class="diya"><path d="M${x - 7},${GROUND - 46} q7,7 14,0 z" fill="#b35a1f"/><path class="sc-diya" d="M${x},${GROUND - 47} q-3,-5 0,-11 q3,6 0,11z" fill="#ffb300"/></g>`;
    }
    return s;
  }

  function birds() {
    const cfg = [[120, 22, 0], [170, 28, -9], [90, 34, -17], [210, 40, -4], [140, 46, -25]];
    return cfg.map(([y, dur, delay], i) => {
      const sc = (.7 + (i % 3) * .2).toFixed(2);
      const flock = [0, 1, 2].slice(0, 1 + (i % 3)).map((j) =>
        `<g transform="translate(${j * 22},${j * 9}) scale(${sc})"><path class="wing" d="M0,0 Q8,-8 16,0 Q24,-8 32,0" fill="none" stroke="#3a2a2a" stroke-width="2.4" stroke-linecap="round" style="animation-delay:${-j * .17}s"/></g>`).join('');
      return `<g class="sc-bird" style="animation-duration:${dur}s;animation-delay:${delay}s"><g transform="translate(0,${y})">${flock}</g></g>`;
    }).join('');
  }

  function stars() {
    let s = '';
    for (let i = 0; i < 60; i++) {
      const x = (i * 157.3) % W, y = (i * 83.7) % 330, r = (i % 4 === 0) ? 1.8 : 1.1;
      s += `<circle class="sc-star" cx="${x.toFixed(1)}" cy="${y.toFixed(1)}" r="${r}" fill="#fff" style="animation-delay:${-(i % 7) * .4}s"/>`;
    }
    return s;
  }

  host.insertAdjacentHTML('afterbegin', `
  <svg viewBox="0 0 ${W} ${H}" preserveAspectRatio="xMidYMax slice" role="img" aria-labelledby="sceneTitle">
    <title id="sceneTitle">Animated temple at the time of day where you are</title>
    <defs>
      <linearGradient id="skyG" x1="0" y1="0" x2="0" y2="1"><stop id="sky0" offset="0"/><stop id="sky1" offset=".65"/><stop id="sky2" offset="1"/></linearGradient>
      <radialGradient id="sunG"><stop offset="0" stop-color="#fff8d6"/><stop offset=".35" stop-color="#ffd54f"/><stop offset="1" stop-color="#ffb300" stop-opacity="0"/></radialGradient>
      <radialGradient id="moonGlow"><stop offset="0" stop-color="#fffbe8" stop-opacity=".55"/><stop offset="1" stop-color="#fffbe8" stop-opacity="0"/></radialGradient>
      <linearGradient id="stoneG" x1="0" y1="0" x2="0" y2="1"><stop id="st0" offset="0" stop-color="#f6cfae"/><stop id="st1" offset="1" stop-color="#e2a982"/></linearGradient>
      <linearGradient id="flagG" x1="0" x2="1"><stop offset="0" stop-color="#ff9800"/><stop offset="1" stop-color="#e65100"/></linearGradient>
      <linearGradient id="goldG" x1="0" x2="0" y1="0" y2="1"><stop offset="0" stop-color="#ffe082"/><stop offset="1" stop-color="#f5b301"/></linearGradient>
      <radialGradient id="templeGlow" cx=".5" cy=".75" r=".5"><stop offset="0" stop-color="#ffcf6b" stop-opacity=".55"/><stop offset="1" stop-color="#ffcf6b" stop-opacity="0"/></radialGradient>
      <style>
        .t-body{fill:url(#stoneG);stroke:#b9724a;stroke-width:1.4}
        .t-line{fill:none;stroke:#b9724a;stroke-width:1;opacity:.75}
        .t-edge{stroke:#a35d36;stroke-width:3}
        .t-gold{fill:url(#goldG);stroke:#c8961a;stroke-width:1}
      </style>
    </defs>
    <rect width="${W}" height="${H}" fill="url(#skyG)"/>
    <g id="starsG" opacity="0">${stars()}</g>
    <g id="moonG" opacity="0"><circle r="70" fill="url(#moonGlow)"/><circle r="26" fill="#fdf6dc"/><path id="moonShade" fill="#1b2350" opacity=".92"/></g>
    <g id="sunG2"><circle r="90" fill="url(#sunG)" class="sc-glow"/><circle r="30" fill="#fff3c4"/></g>
    <g opacity=".85">
      <g class="sc-cloud" style="animation-duration:90s"><ellipse cx="120" cy="150" rx="70" ry="16" fill="#fff" opacity=".7"/><ellipse cx="160" cy="140" rx="40" ry="14" fill="#fff" opacity=".7"/></g>
      <g class="sc-cloud" style="animation-duration:120s;animation-delay:-60s"><ellipse cx="80" cy="230" rx="90" ry="14" fill="#fff" opacity=".55"/></g>
    </g>
    <path d="M0,520 C120,470 220,500 330,480 C460,455 560,500 690,470 C820,445 900,490 1000,470 L1000,${H} L0,${H} Z" id="hillsFar" fill="#e9c79e" opacity=".7"/>
    <ellipse cx="500" cy="470" rx="420" ry="200" fill="url(#templeGlow)" id="templeGlowE" opacity="0"/>
    <g id="temple">${templeSVG()}</g>
    <path d="M0,${GROUND} L${W},${GROUND} L${W},${H} L0,${H} Z" id="groundP" fill="#e8c9a0"/>
    <path d="M0,${GROUND + 30} C200,${GROUND + 20} 300,${GROUND + 42} 500,${GROUND + 32} C700,${GROUND + 22} 820,${GROUND + 44} 1000,${GROUND + 30}" stroke="#d8b385" stroke-width="2" fill="none"/>
    <g id="birdsG">${birds()}</g>
    <rect id="nightTint" width="${W}" height="${H}" fill="#0d1440" opacity="0" style="mix-blend-mode:multiply"/>
  </svg>
  <div class="scene-badge"><span data-scene-label>—</span><small data-scene-place>Ayodhya</small></div>
  <div class="scene-time" role="group" aria-label="Preview time of day">
    <button type="button" data-t="auto" aria-pressed="true" title="Live (your local time)">Live</button>
    <button type="button" data-t="dawn" title="Sunrise">🌅</button>
    <button type="button" data-t="noon" title="Afternoon">☀️</button>
    <button type="button" data-t="dusk" title="Sunset">🌇</button>
    <button type="button" data-t="night" title="Night">🌙</button>
  </div>`);

  const $ = (id) => document.getElementById(id);
  const lerp = (a, b, t) => a + (b - a) * t;
  const hex = (h) => [1, 3, 5].map((i) => parseInt(h.slice(i, i + 2), 16));
  const mix = (c1, c2, t) => { const a = hex(c1), b = hex(c2); return `rgb(${a.map((v, i) => Math.round(lerp(v, b[i], t))).join(',')})`; };
  const mix3 = (c1, c2, c3, wDay, wTw) => { // blend night → twilight → day
    const base = mix(c1, c2, Math.min(1, wTw + wDay));
    const toHex = (rgb) => '#' + rgb.match(/\d+/g).map((n) => (+n).toString(16).padStart(2, '0')).join('');
    return mix(toHex(base), c3, wDay);
  };
  const PAL = {
    night: ['#0b1638', '#1f2a5c', '#3a3a6e'],
    twilight: ['#9a7fc9', '#ffa36c', '#ffd89a'],
    day: ['#9fd6ff', '#fde9c4', '#fff1d6']
  };

  // Location: Ayodhya by default; the hour hand follows the visitor's own clock
  const place = { lat: 26.79, lon: 82.2, name: 'Ayodhya' };
  let forced = null;

  function render() {
    const now = new Date();
    const tz = -now.getTimezoneOffset() / 60;
    // Use the visitor's longitude implied by their time zone, so "local time" feels right anywhere
    const lon = tz * 15 + (tz === 5.5 ? -0.3 : 0);
    const st = A.sunTimes(now, place.lat, lon, tz);
    let mins = now.getHours() * 60 + now.getMinutes() + now.getSeconds() / 60;
    if (forced === 'dawn') mins = st.sunrise + 12;
    if (forced === 'noon') mins = (st.sunrise + st.sunset) / 2 + 90;
    if (forced === 'dusk') mins = st.sunset - 10;
    if (forced === 'night') mins = (st.sunset + 180) % 1440;

    const dayLen = st.sunset - st.sunrise;
    const f = (mins - st.sunrise) / dayLen; // 0..1 during the day
    const alt = Math.sin(Math.PI * Math.min(Math.max(f, -0.2), 1.2)); // >0 daytime
    const wDay = Math.max(0, Math.min(1, (alt - 0.05) / 0.35));
    const wTw = Math.max(0, 1 - Math.abs(alt) / 0.28) * (f > -0.15 && f < 1.15 ? 1 : 0);

    $('sky0').setAttribute('stop-color', mix3(PAL.night[0], PAL.twilight[0], PAL.day[0], wDay, wTw));
    $('sky1').setAttribute('stop-color', mix3(PAL.night[1], PAL.twilight[1], PAL.day[1], wDay, wTw));
    $('sky2').setAttribute('stop-color', mix3(PAL.night[2], PAL.twilight[2], PAL.day[2], wDay, wTw));

    // Sun along its arc
    const sun = $('sunG2');
    const sx = lerp(60, 940, f), sy = 560 - Math.max(-0.3, alt) * 470;
    sun.setAttribute('transform', `translate(${sx},${sy})`);
    sun.setAttribute('opacity', f > -0.08 && f < 1.08 ? 1 : 0);

    // Night: moon along the night arc with its real phase; stars
    const nightLen = 1440 - dayLen;
    let nf = (mins - st.sunset) / nightLen; if (nf < 0) nf += 1440 / nightLen; nf = nf % 1;
    const night = 1 - Math.min(1, wDay + wTw);
    const moon = $('moonG');
    moon.setAttribute('transform', `translate(${lerp(80, 920, nf)},${560 - Math.sin(Math.PI * nf) * 450})`);
    moon.setAttribute('opacity', night.toFixed(2));
    $('starsG').setAttribute('opacity', (night * .95).toFixed(2));
    const lm = A.limbs(A.jdFromDate(now));
    // shade = dark part of the disc. waxing (elong<180): light on the right
    const k = Math.cos(lm.elong * Math.PI / 180); // 1 new, -1 full
    const r = 26, waxing = lm.elong < 180;
    const sweepOuter = waxing ? 0 : 1;
    const rx = Math.abs(k) * r;
    const sweepInner = (k > 0) === waxing ? 1 : 0;
    $('moonShade').setAttribute('d', `M0,${-r} A${r},${r} 0 0 ${sweepOuter} 0,${r} A${rx},${r} 0 0 ${sweepInner} 0,${-r} Z`);

    // Lighting of temple and ground
    $('nightTint').setAttribute('opacity', (night * .55).toFixed(2));
    $('templeGlowE').setAttribute('opacity', (night * .9 + wTw * .3).toFixed(2));
    $('st0').setAttribute('stop-color', mix('#e9a77a', '#f6cfae', wDay));
    $('st1').setAttribute('stop-color', mix('#c47a52', '#e2a982', wDay));
    document.querySelectorAll('#temple .diya').forEach((d) => d.setAttribute('opacity', (0.35 + night * .65).toFixed(2)));

    const label = document.querySelector('[data-scene-label]');
    const hi = document.documentElement.lang === 'hi';
    const phase = wDay > .6 ? (hi ? 'दिन' : 'Day') : wTw > .3 ? (f < .5 ? (hi ? 'सूर्योदय' : 'Sunrise') : (hi ? 'सूर्यास्त' : 'Sunset')) : (hi ? 'रात्रि' : 'Night');
    label.textContent = `${phase} · ${A.fmtMin(mins)}`;
    document.querySelector('[data-scene-place]').textContent = (hi ? 'सूर्योदय ' : 'Sunrise ') + A.fmtMin(st.sunrise) + (hi ? ' · सूर्यास्त ' : ' · Sunset ') + A.fmtMin(st.sunset);
  }

  host.querySelectorAll('.scene-time button').forEach((b) => b.addEventListener('click', () => {
    forced = b.dataset.t === 'auto' ? null : b.dataset.t;
    host.querySelectorAll('.scene-time button').forEach((x) => x.setAttribute('aria-pressed', String(x === b)));
    render();
  }));
  render();
  setInterval(render, 20000);
  document.addEventListener('langchange', render);
})();

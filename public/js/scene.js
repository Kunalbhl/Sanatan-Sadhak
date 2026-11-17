// Animated temple scene, in the spirit of Ayodhya's Shri Ram Mandir: a three-storey Nagara
// temple in pink sandstone with pillared colonnades, pyramidal mandap roofs and a tall
// clustered shikhara with its dhwaja. The sky follows the visitor's local time
// (sunrise, day, sunset, night with the real moon phase). A Hanuman langur sits on
// the shikhar, birds fly across and the flag waves. Motion is driven by JavaScript so it
// keeps moving even when the OS has "animations off" (it is slow and gentle).
(function () {
  const host = document.querySelector('[data-scene]');
  if (!host || !window.SadhakAstro) return;
  const A = window.SadhakAstro;
  const W = 1000, H = 690, G = 610;
  const CX = 500;
  const f = (n) => n.toFixed(1);

  function colonnade(x1, x2, top, bot, n, shade) {
    const step = (x2 - x1) / n;
    let s = `<rect x="${f(x1)}" y="${f(top)}" width="${f(x2 - x1)}" height="${f(bot - top)}" fill="url(#${shade})"/>`;
    for (let i = 0; i < n; i++) {
      const a = x1 + i * step + step * 0.18, b = x1 + (i + 1) * step - step * 0.18;
      const at = top + (bot - top) * 0.28;
      s += `<path d="M${f(a)},${f(bot)} L${f(a)},${f(at + 6)} Q${f(a)},${f(at)} ${f((a + b) / 2)},${f(at - 4)} Q${f(b)},${f(at)} ${f(b)},${f(at + 6)} L${f(b)},${f(bot)} Z" fill="url(#recess)"/>`;
    }
    for (let i = 0; i <= n; i++) {
      const x = x1 + i * step;
      s += `<rect x="${f(x - step * 0.18)}" y="${f(top)}" width="${f(step * 0.36)}" height="${f(bot - top)}" fill="url(#${shade})"/>`;
      s += `<path d="M${f(x - step * .24)},${f(top + 5)} h${f(step * .48)} M${f(x - step * .24)},${f(bot - 4)} h${f(step * .48)}" class="t-cut"/>`;
      s += `<path d="M${f(x)},${f(top + 9)} V${f(bot - 8)}" class="t-cut" opacity=".45"/>`;
    }
    s += `<rect x="${f(x1 - 8)}" y="${f(top - 9)}" width="${f(x2 - x1 + 16)}" height="9" rx="2" fill="url(#${shade})" class="t-edge"/>`;
    s += `<rect x="${f(x1 - 6)}" y="${f(top)}" width="${f(x2 - x1 + 12)}" height="3" fill="#8e4a2c" opacity=".45"/>`;
    return s;
  }
  function band(x1, x2, y, h, shade) {
    return `<rect x="${f(x1)}" y="${f(y)}" width="${f(x2 - x1)}" height="${f(h)}" fill="url(#${shade})"/>` +
      `<path d="M${f(x1)},${f(y + h * .35)} H${f(x2)} M${f(x1)},${f(y + h * .7)} H${f(x2)}" class="t-cut" opacity=".55"/>`;
  }
  function shikhara(cx, base, top, half, bands, shade) {
    const h = base - top;
    let s = `<path d="M${f(cx - half)},${f(base)} C${f(cx - half)},${f(base - h * .5)} ${f(cx - half * .7)},${f(top + h * .18)} ${f(cx - half * .3)},${f(top)} L${f(cx + half * .3)},${f(top)} C${f(cx + half * .7)},${f(top + h * .18)} ${f(cx + half)},${f(base - h * .5)} ${f(cx + half)},${f(base)} Z" fill="url(#${shade})" class="t-edge"/>`;
    for (let i = 1; i < bands; i++) {
      const t = i / bands, y = base - h * t;
      const w = half * (0.3 + 0.7 * Math.pow(1 - t, 0.62));
      s += `<path d="M${f(cx - w)},${f(y)} Q${f(cx)},${f(y + 3)} ${f(cx + w)},${f(y)}" class="t-cut"/>`;
    }
    for (const k of [-.62, -.3, 0, .3, .62]) {
      s += `<path d="M${f(cx + k * half)},${f(base)} C${f(cx + k * half)},${f(base - h * .5)} ${f(cx + k * half * .65)},${f(top + h * .2)} ${f(cx + k * half * .28)},${f(top + 4)}" class="t-cut" opacity="${k === 0 ? .8 : .5}"/>`;
    }
    return s;
  }
  function amalaka(cx, y, r) {
    let s = `<ellipse cx="${cx}" cy="${y}" rx="${r}" ry="${f(r * .42)}" fill="url(#stoneL)" class="t-edge"/>`;
    for (let k = -3; k <= 3; k++) s += `<path d="M${f(cx + k * r / 4)},${f(y - r * .38)} V${f(y + r * .38)}" class="t-cut" opacity=".6"/>`;
    return s;
  }
  function kalash(cx, y, sz) {
    return `<ellipse cx="${cx}" cy="${f(y - sz * .55)}" rx="${f(sz * .62)}" ry="${f(sz * .55)}" fill="url(#gold)"/>` +
      `<rect x="${f(cx - sz * .3)}" y="${f(y - sz * 1.4)}" width="${f(sz * .6)}" height="${f(sz * .4)}" fill="url(#gold)"/>` +
      `<path d="M${cx},${f(y - sz * 1.4)} q${f(-sz * .25)},${f(-sz * .4)} 0,${f(-sz * .85)} q${f(sz * .25)},${f(sz * .45)} 0,${f(sz * .85)}" fill="url(#gold)"/>`;
  }
  function samvarana(cx, base, half, tiers, shade) {
    let s = '';
    const th = 11;
    for (let i = 0; i < tiers; i++) {
      const w = half * (1 - i / (tiers + 1.2)), y = base - i * th;
      s += `<path d="M${f(cx - w)},${f(y)} L${f(cx - w + 6)},${f(y - th)} L${f(cx + w - 6)},${f(y - th)} L${f(cx + w)},${f(y)} Z" fill="url(#${shade})" class="t-edge"/>`;
      for (let k = -2; k <= 2; k++) s += `<circle cx="${f(cx + k * w / 2.6)}" cy="${f(y - th / 2)}" r="1.6" fill="#a75a36" opacity=".55"/>`;
    }
    const topY = base - tiers * th;
    s += `<path d="M${f(cx - half * .32)},${f(topY)} Q${cx},${f(topY - half * .55)} ${f(cx + half * .32)},${f(topY)} Z" fill="url(#${shade})" class="t-edge"/>`;
    return s + kalash(cx, topY - half * .42, 7);
  }

  function temple() {
    let s = '';
    s += `<path d="M110,${G} L890,${G} L872,${G - 12} L128,${G - 12} Z" fill="url(#stoneS)" class="t-edge"/>`;
    s += `<path d="M140,${G - 12} L860,${G - 12} L846,${G - 24} L154,${G - 24} Z" fill="url(#stoneM)" class="t-edge"/>`;
    s += band(170, 830, G - 46, 22, 'stoneM');
    for (let i = 0; i < 6; i++) s += `<rect x="${458 - i * 4}" y="${f(G - 46 + i * 7.6)}" width="${84 + i * 8}" height="7.6" fill="url(#stoneL)" class="t-edge"/>`;
    s += colonnade(190, 400, G - 118, G - 46, 9, 'stoneS');
    s += colonnade(600, 810, G - 118, G - 46, 9, 'stoneS');
    s += colonnade(400, 600, G - 132, G - 46, 7, 'stoneM');
    s += colonnade(222, 390, G - 178, G - 127, 7, 'stoneM');
    s += colonnade(610, 778, G - 178, G - 127, 7, 'stoneM');
    s += colonnade(410, 590, G - 196, G - 141, 6, 'stoneL');
    s += colonnade(428, 572, G - 248, G - 205, 5, 'stoneL');
    s += samvarana(268, G - 187, 52, 4, 'stoneM');
    s += samvarana(732, G - 187, 52, 4, 'stoneM');
    s += samvarana(352, G - 187, 34, 3, 'stoneS');
    s += samvarana(648, G - 187, 34, 3, 'stoneS');
    for (const x of [200, 800]) s += shikhara(x, G - 127, G - 214, 15, 6, 'stoneS') + amalaka(x, G - 216, 8) + kalash(x, G - 220, 5);
    s += shikhara(CX - 74, G - 257, G - 352, 26, 6, 'stoneS');
    s += shikhara(CX + 74, G - 257, G - 352, 26, 6, 'stoneS');
    s += shikhara(CX - 46, G - 257, G - 395, 30, 8, 'stoneM');
    s += shikhara(CX + 46, G - 257, G - 395, 30, 8, 'stoneM');
    s += shikhara(CX, G - 257, 148, 74, 14, 'stoneL');
    s += `<path d="M${CX},${G - 257} L${CX + 74},${G - 257} C${CX + 74},${G - 400} ${CX + 52},180 ${CX + 22},148 L${CX},148 Z" fill="#7a3d22" opacity=".14"/>`;
    s += amalaka(CX, 144, 28);
    s += kalash(CX, 130, 13);
    s += `<path d="M${CX},112 V30" stroke="#7a4a1c" stroke-width="3.2" stroke-linecap="round"/>`;
    s += `<path id="scFlag" fill="url(#flagG)" stroke="#bf360c" stroke-width=".8"/>`;
    // Hanuman langur: silver-grey fur, black face, long curling tail
    s += `<g transform="translate(${CX - 70},214)">
      <path id="scTail" fill="none" stroke="#8d8d86" stroke-width="4.2" stroke-linecap="round"/>
      <path d="M-4,28 C-10,10 -6,-6 6,-10 C18,-14 28,-4 28,12 C28,24 22,32 8,32 Z" fill="url(#fur)"/>
      <path d="M2,30 C-6,30 -12,36 -8,40 L10,40 Z" fill="#b8b6ac"/><path d="M18,30 C26,30 32,36 28,40 L12,40 Z" fill="#b8b6ac"/>
      <path d="M22,6 C30,12 30,22 26,30" stroke="#a9a79d" stroke-width="4.5" fill="none" stroke-linecap="round"/>
      <path d="M0,8 C-6,16 -4,24 2,30" stroke="#a9a79d" stroke-width="4.5" fill="none" stroke-linecap="round"/>
      <g id="scHead">
        <circle cx="12" cy="-16" r="11.5" fill="url(#fur)"/>
        <path d="M2,-20 C4,-27 20,-27 22,-20" stroke="#ecece4" stroke-width="3" fill="none"/>
        <ellipse cx="12" cy="-13" rx="7.2" ry="7.8" fill="#1d1a1a"/>
        <circle cx="9.2" cy="-15" r="1.3" fill="#e9c46a"/><circle cx="14.8" cy="-15" r="1.3" fill="#e9c46a"/>
        <path d="M10,-9 Q12,-7.6 14,-9" stroke="#5a4f4f" stroke-width="1" fill="none"/>
        <circle cx="1.5" cy="-15" r="2.4" fill="#1d1a1a"/><circle cx="22.5" cy="-15" r="2.4" fill="#1d1a1a"/>
      </g></g>`;
    for (let x = 200; x <= 800; x += 50) {
      if (x > 450 && x < 550) continue;
      s += `<g class="diya"><path d="M${x - 7},${G - 47} q7,7 14,0 z" fill="#9c4a18"/><path d="M${x},${G - 48} q-3,-6 0,-12 q3,7 0,12z" fill="#ffb300"/></g>`;
    }
    return s;
  }
  function stars() {
    let s = '';
    for (let i = 0; i < 70; i++) s += `<circle class="st" cx="${f((i * 157.3) % W)}" cy="${f((i * 83.7) % 330)}" r="${i % 5 === 0 ? 1.8 : 1.05}" fill="#fff"/>`;
    return s;
  }

  host.insertAdjacentHTML('afterbegin', `
  <svg viewBox="0 0 ${W} ${H}" preserveAspectRatio="xMidYMax slice" role="img" aria-labelledby="sceneTitle">
    <title id="sceneTitle">Shri Ram Mandir at the time of day where you are</title>
    <defs>
      <linearGradient id="skyG" x1="0" y1="0" x2="0" y2="1"><stop id="sky0" offset="0"/><stop id="sky1" offset=".62"/><stop id="sky2" offset="1"/></linearGradient>
      <radialGradient id="sunG"><stop offset="0" stop-color="#fffbe6"/><stop offset=".3" stop-color="#ffe082"/><stop offset="1" stop-color="#ffb300" stop-opacity="0"/></radialGradient>
      <radialGradient id="moonGlow"><stop offset="0" stop-color="#fffbe8" stop-opacity=".5"/><stop offset="1" stop-color="#fffbe8" stop-opacity="0"/></radialGradient>
      <linearGradient id="stoneL" x1="0" x2="1"><stop class="sL0" offset="0"/><stop class="sL1" offset=".55"/><stop class="sL2" offset="1"/></linearGradient>
      <linearGradient id="stoneM" x1="0" x2="1"><stop class="sM0" offset="0"/><stop class="sM1" offset=".55"/><stop class="sM2" offset="1"/></linearGradient>
      <linearGradient id="stoneS" x1="0" x2="1"><stop class="sS0" offset="0"/><stop class="sS1" offset=".55"/><stop class="sS2" offset="1"/></linearGradient>
      <linearGradient id="recess" x1="0" y1="0" x2="0" y2="1"><stop offset="0" stop-color="#6e3520" stop-opacity=".75"/><stop offset="1" stop-color="#8a4a2e" stop-opacity=".55"/></linearGradient>
      <linearGradient id="gold" x1="0" x2="1"><stop offset="0" stop-color="#fff0a8"/><stop offset=".5" stop-color="#f5b301"/><stop offset="1" stop-color="#b8860b"/></linearGradient>
      <linearGradient id="flagG" x1="0" x2="1"><stop offset="0" stop-color="#ff9800"/><stop offset="1" stop-color="#e65100"/></linearGradient>
      <radialGradient id="fur" cx=".4" cy=".35"><stop offset="0" stop-color="#e6e4dc"/><stop offset=".7" stop-color="#b9b7ad"/><stop offset="1" stop-color="#8f8d84"/></radialGradient>
      <radialGradient id="tGlow" cx=".5" cy=".75" r=".5"><stop offset="0" stop-color="#ffcf6b" stop-opacity=".6"/><stop offset="1" stop-color="#ffcf6b" stop-opacity="0"/></radialGradient>
      <filter id="halo" x="-30%" y="-30%" width="160%" height="160%"><feFlood id="haloC" flood-color="#ffd27a"/><feComposite in2="SourceAlpha" operator="in"/><feGaussianBlur stdDeviation="14"/></filter>
      <filter id="tint"><feFlood id="tintC" flood-color="#2a140e" flood-opacity="0"/><feComposite in2="SourceAlpha" operator="in"/></filter>
      <filter id="rim"><feFlood id="rimC" flood-color="#ffcf70" flood-opacity="0"/><feComposite in2="SourceAlpha" operator="in"/><feOffset dx="-2" dy="-1"/><feComposite in2="SourceAlpha" operator="in"/></filter>
      <linearGradient id="haze" x1="0" y1="0" x2="0" y2="1"><stop offset="0" stop-color="#fff" stop-opacity="0"/><stop id="hazeC" offset="1" stop-color="#ffd9a0" stop-opacity=".55"/></linearGradient>
      <linearGradient id="rayG" x1="0" x2="1"><stop offset="0" stop-color="#fff3c4" stop-opacity=".55"/><stop offset="1" stop-color="#fff3c4" stop-opacity="0"/></linearGradient>
      <style>.t-edge{stroke:#a35d36;stroke-width:1}.t-cut{fill:none;stroke:#9a5634;stroke-width:1}</style>
    </defs>
    <rect width="${W}" height="${H}" fill="url(#skyG)"/>
    <g id="starsG" opacity="0">${stars()}</g>
    <g id="moonG" opacity="0"><circle r="74" fill="url(#moonGlow)"/><circle r="24" fill="#fdf6dc"/><path id="moonShade" fill="#1b2350" opacity=".9"/></g>
    <g id="sunG2"><circle r="95" fill="url(#sunG)"/><circle r="28" fill="#fff6cf"/></g>
    <g opacity=".8"><g id="c1"><ellipse cx="0" cy="150" rx="70" ry="15" fill="#fff" opacity=".75"/><ellipse cx="40" cy="140" rx="40" ry="14" fill="#fff" opacity=".75"/></g>
      <g id="c2"><ellipse cx="0" cy="235" rx="90" ry="12" fill="#fff" opacity=".55"/></g></g>
    <path d="M0,540 C120,500 220,522 330,505 C460,485 560,520 690,495 C820,470 900,512 1000,492 L1000,${H} L0,${H} Z" fill="#e9c79e" opacity=".55"/>
    <g id="farT" opacity=".55">${shikhara(140, 560, 400, 34, 7, 'stoneS')}${shikhara(205, 560, 450, 22, 5, 'stoneS')}${shikhara(860, 560, 410, 32, 7, 'stoneS')}${shikhara(795, 560, 460, 20, 5, 'stoneS')}</g>
    <rect id="farHaze" x="0" y="380" width="${W}" height="200" fill="url(#haze)"/>
    <g id="raysG" opacity="0"></g>
    <ellipse cx="500" cy="470" rx="440" ry="220" fill="url(#tGlow)" id="tGlowE" opacity="0"/>
    <use href="#temple" filter="url(#halo)" id="haloUse" opacity=".9"/>
    <g id="temple">${temple()}</g>
    <use href="#temple" filter="url(#tint)"/>
    <rect x="0" y="440" width="${W}" height="${G - 440}" fill="url(#haze)" id="nearHaze" opacity=".6"/>
    <g id="dustG"></g>
    <rect x="0" y="${G}" width="${W}" height="${H - G}" fill="#e6c79c" id="groundR"/>
    <path d="M0,${G + 28} C200,${G + 18} 300,${G + 40} 500,${G + 30} C700,${G + 20} 820,${G + 42} 1000,${G + 28}" stroke="#d4ae80" stroke-width="2" fill="none"/>
    <g id="birdsG"></g>
    <rect id="nightTint" width="${W}" height="${H}" fill="#0d1440" opacity="0"/>
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

  // Birds: real silhouettes (body, two flapping wings, tail)
  const NS = 'http://www.w3.org/2000/svg';
  const birds = [];
  [[110, 24, 4], [175, 31, 3], [80, 38, 2], [215, 27, 5], [140, 45, 1]].forEach(([y, dur, n], fi) => {
    for (let j = 0; j < n; j++) {
      const p = document.createElementNS(NS, 'path');
      p.setAttribute('fill', '#2f2626');
      $('birdsG').appendChild(p);
      birds.push({ el: p, y: y + j * 10 + (j % 2) * 6, dx: j * 26 + (j % 2) * 8, dur, start: -fi * 6.3, phase: Math.random() * 6, scale: 0.75 + (fi % 3) * 0.18 });
    }
  });
  function birdPath(x, y, s, w) {
    const tip = -9 * w * s, mid = -4 * w * s;
    return `M${f(x)},${f(y)} c${f(4 * s)},${f(-1 * s)} ${f(7 * s)},${f(-1 * s)} ${f(11 * s)},0 c${f(-3 * s)},${f(1.5 * s)} ${f(-8 * s)},${f(1.5 * s)} ${f(-11 * s)},0 Z` +
      ` M${f(x + 5 * s)},${f(y)} Q${f(x + 1 * s)},${f(y + mid)} ${f(x - 9 * s)},${f(y + tip)} Q${f(x - 1 * s)},${f(y + mid * .4 + s)} ${f(x + 5 * s)},${f(y + 1.6 * s)} Z` +
      ` M${f(x + 6 * s)},${f(y)} Q${f(x + 9 * s)},${f(y + mid)} ${f(x + 18 * s)},${f(y + tip)} Q${f(x + 10 * s)},${f(y + mid * .4 + s)} ${f(x + 6 * s)},${f(y + 1.6 * s)} Z` +
      ` M${f(x + 11 * s)},${f(y)} l${f(3 * s)},${f(-1.4 * s)} l${f(-1 * s)},${f(2.4 * s)} Z`;
  }

  // Sky by local time
  const lerp = (a, b, t) => a + (b - a) * t;
  const hex = (h) => [1, 3, 5].map((i) => parseInt(h.slice(i, i + 2), 16));
  const mix = (c1, c2, t) => { const a = hex(c1), b = hex(c2); return '#' + a.map((v, i) => Math.round(lerp(v, b[i], t)).toString(16).padStart(2, '0')).join(''); };
  const sky3 = (n, tw, d, wD, wT) => mix(mix(n, tw, Math.min(1, wT + wD)), d, wD);
  const PAL = { night: ['#0b1638', '#1f2a5c', '#3a3a6e'], tw: ['#8c74c4', '#ff9e66', '#ffd89a'], day: ['#8fcdf8', '#cfe9f7', '#fdf0d8'] };
  let forced = null, light = { night: 0 }, sunPos = { x: 500, y: 300 }, dustOn = true;
  const setStops = (cls, c) => document.querySelectorAll('.' + cls).forEach((e) => e.setAttribute('stop-color', c));

  function sky() {
    const now = new Date();
    const tz = -now.getTimezoneOffset() / 60;
    const st = A.sunTimes(now, 26.79, tz * 15, tz);
    let mins = now.getHours() * 60 + now.getMinutes() + now.getSeconds() / 60;
    const PH = { brahma: st.sunrise - 96, dawn: st.sunrise + 80, noon: (st.sunrise + st.sunset) / 2 + 40, dusk: st.sunset - 6, night: (st.sunset + 220) % 1440 };
    if (forced) mins = PH[forced];
    Object.entries(PH).forEach(([k, v]) => { const e = document.querySelector(`[data-ph="${k}"]`); if (e) e.textContent = A.fmtMin(v); });
    if (false) mins = st.sunrise + 12;
    const dayLen = st.sunset - st.sunrise;
    const fr = (mins - st.sunrise) / dayLen;
    const alt = Math.sin(Math.PI * Math.min(Math.max(fr, -0.2), 1.2));
    const wD = Math.max(0, Math.min(1, (alt - 0.05) / 0.35));
    const wT = (fr > -0.15 && fr < 1.15) ? Math.max(0, 1 - Math.abs(alt) / 0.28) : 0;
    const night = 1 - Math.min(1, wD + wT);
    light = { night };
    ['sky0', 'sky1', 'sky2'].forEach((id, i) => $(id).setAttribute('stop-color', sky3(PAL.night[i], PAL.tw[i], PAL.day[i], wD, wT)));
    $('sunG2').setAttribute('transform', `translate(${f(lerp(70, 930, fr))},${f(560 - Math.max(-0.3, alt) * 430)})`);
    $('sunG2').setAttribute('opacity', fr > -0.08 && fr < 1.08 ? 1 : 0);
    let nf = (mins - st.sunset) / (1440 - dayLen); if (nf < 0) nf += 1440 / (1440 - dayLen); nf %= 1;
    $('moonG').setAttribute('transform', `translate(${f(lerp(90, 910, nf))},${f(560 - Math.sin(Math.PI * nf) * 430)})`);
    $('moonG').setAttribute('opacity', night.toFixed(2));
    $('starsG').setAttribute('opacity', (night * .95).toFixed(2));
    const lm = A.limbs(A.jdFromDate(now));
    const k = Math.cos(lm.elong * Math.PI / 180), r = 24, waxing = lm.elong < 180;
    $('moonShade').setAttribute('d', `M0,${-r} A${r},${r} 0 0 ${waxing ? 0 : 1} 0,${r} A${f(Math.abs(k) * r)},${r} 0 0 ${(k > 0) === waxing ? 1 : 0} 0,${-r} Z`);
    const tone = (day, dusk) => mix(dusk, day, wD);
    setStops('sL0', tone('#f7d6bd', '#d99a78')); setStops('sL1', tone('#efc2a2', '#c98663')); setStops('sL2', tone('#d9a07c', '#a8684a'));
    setStops('sM0', tone('#f2cbae', '#d08f6d')); setStops('sM1', tone('#e8b693', '#bf7c5a')); setStops('sM2', tone('#cf9370', '#9d5f42'));
    setStops('sS0', tone('#eabd9d', '#c7845f')); setStops('sS1', tone('#dea886', '#b47152')); setStops('sS2', tone('#c4855f', '#92573a'));
    $('nightTint').setAttribute('opacity', (night * .42).toFixed(2));
    $('tGlowE').setAttribute('opacity', (night * .95 + wT * .25).toFixed(2));
    $('groundR').setAttribute('fill', mix('#a88a74', '#e6c79c', wD));
    document.querySelectorAll('#temple .diya').forEach((d) => d.setAttribute('opacity', (0.3 + night * .7).toFixed(2)));
    // Backlit look: temple turns to a warm silhouette when the sun is low, glows at night
    const low = Math.max(wT, night * .7);
    $('tintC').setAttribute('flood-opacity', (low * .5).toFixed(2));
    $('tintC').setAttribute('flood-color', night > .5 ? '#0e1230' : '#3a160c');
    $('haloC').setAttribute('flood-color', night > .5 ? '#ffc56b' : wT > .3 ? '#ffb347' : '#fff1c7');
    $('haloUse').setAttribute('opacity', (0.35 + wT * .65 + night * .5).toFixed(2));
    $('hazeC').setAttribute('stop-color', night > .5 ? '#3b3f7a' : wT > .3 ? '#ffb070' : '#fff0d0');
    $('farT').setAttribute('opacity', (0.35 + low * .3).toFixed(2));
    $('raysG').setAttribute('opacity', (wT * .9 + wD * .35).toFixed(2));
    sunPos = { x: lerp(70, 930, fr), y: 560 - Math.max(-0.3, alt) * 430 };
    dustOn = night < .5;

    const hi = document.documentElement.lang === 'hi';
    const isBrahma = mins >= st.sunrise - 96 && mins < st.sunrise - 48;
    const phase = isBrahma ? (hi ? 'ब्रह्म मुहूर्त' : 'Brahma Muhurta') : wD > .6 ? (fr < .45 ? (hi ? 'प्रातः' : 'Morning') : (hi ? 'अपराह्न' : 'Afternoon')) : wT > .3 ? (fr < .5 ? (hi ? 'सूर्योदय' : 'Sunrise') : (hi ? 'सन्ध्या' : 'Sandhya')) : (hi ? 'रात्रि' : 'Night');
    document.querySelector('[data-scene-label]').textContent = `${phase} · ${A.fmtMin(mins)}`;
    const greet = isBrahma ? ['Shubh Brahma Muhurta', 'शुभ ब्रह्म मुहूर्त'] : wD > .6 && fr < .45 ? ['Suprabhatam, Devotee', 'सुप्रभातम्, साधक'] : wD > .6 ? ['Welcome Devotee', 'स्वागतम् साधक'] : wT > .3 && fr > .5 ? ['Shubh Sandhya', 'शुभ सन्ध्या'] : (fr > 0 && fr < .5) ? ['Suprabhatam, Devotee', 'सुप्रभातम्, साधक'] : (fr >= .5 && fr < 1.1) ? ['Shubh Sandhya', 'शुभ सन्ध्या'] : ['Shubh Ratri', 'शुभ रात्रि'];
    document.querySelector('[data-scene-greet]').textContent = hi ? greet[1] : greet[0];
    const d = new Date();
    document.querySelector('[data-scene-date]').textContent = d.toLocaleDateString(hi ? 'hi-IN' : 'en-IN', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric' }) + ' • ' + A.fmtMin(mins);
    // countdown to the next sandhya: sunrise, midday, sunset (the three sandhya times)
    const nowM = d.getHours() * 60 + d.getMinutes();
    const nexts = [[st.sunrise - 96, hi ? 'ब्रह्म मुहूर्त' : 'Brahma Muhurta'], [st.sunrise, hi ? 'प्रातः सन्ध्या' : 'Pratah Sandhya'], [st.noon, hi ? 'मध्याह्न' : 'Madhyahna'], [st.sunset, hi ? 'सायं सन्ध्या' : 'Sayam Sandhya']];
    let nx = nexts.find(([m]) => m > nowM) || [nexts[0][0] + 1440, nexts[0][1]];
    const dm = Math.round(nx[0] - nowM);
    document.querySelector('[data-scene-next]').textContent = `⏱ ${nx[1]} ${hi ? 'में' : 'in'} ${String(Math.floor(dm / 60)).padStart(2, '0')}:${String(dm % 60).padStart(2, '0')}`;
  }

  // god rays (thin wedges from the sun) and golden dust motes
  const rays = [];
  for (let i = 0; i < 12; i++) { const p = document.createElementNS(NS, 'path'); p.setAttribute('fill', 'url(#rayG)'); $('raysG').appendChild(p); rays.push({ el: p, a: i * 30, w: 3 + (i % 3) * 2 }); }
  const dust = [];
  for (let i = 0; i < 28; i++) { const c = document.createElementNS(NS, 'circle'); c.setAttribute('fill', '#ffe08a'); $('dustG').appendChild(c); dust.push({ el: c, x: Math.random() * W, y: 200 + Math.random() * 380, r: .8 + Math.random() * 1.8, v: .1 + Math.random() * .3, p: Math.random() * 6 }); }
  const NAMES = ['श्री राम • रामचंद्र • रघुनाथ • राघव', 'जानकीवल्लभ • दशरथनंदन • कौसल्यानंदन', 'सीताराम • रघुपति राघव राजा राम', 'मर्यादा पुरुषोत्तम • कोदंडधारी'];
  let nameI = 0; setInterval(() => { nameI = (nameI + 1) % NAMES.length; const e = document.querySelector('[data-scene-names]'); if (e) { e.style.opacity = 0; setTimeout(() => { e.textContent = NAMES[nameI]; e.style.opacity = 1; }, 400); } }, 4000);
  const flag = $('scFlag'), tail = $('scTail'), head = $('scHead');
  let visible = true, headR = 0;
  if ('IntersectionObserver' in window) new IntersectionObserver((e) => { visible = e[0].isIntersecting; }).observe(host);
  const starEls = [...document.querySelectorAll('#starsG .st')];

  function frame(ms) {
    const t = ms / 1000;
    if (visible) {
      // Flag: travelling wave growing toward the free end, swallow-tail notch
      const fx = CX + 1, fy = 34, len = 78, hgt = 40;
      let top = `M${fx},${fy}`, bot = '';
      for (let i = 1; i <= 12; i++) {
        const x = (i / 12) * len, wv = Math.sin(t * 5 - x / 11) * (x / len) * 7;
        top += ` L${f(fx + x)},${f(fy + (x / len) * 8 + wv)}`;
        bot = ` L${f(fx + x)},${f(fy + hgt - (x / len) * 10 + wv)}` + bot;
      }
      const endW = Math.sin(t * 5 - len / 11) * 7;
      flag.setAttribute('d', `${top} L${f(fx + len - 20)},${f(fy + hgt / 2 + endW)}${bot} L${fx},${fy + hgt} Z`);
      // Monkey: tail sways, head looks around
      const sw = Math.sin(t * 1.3) * 10;
      tail.setAttribute('d', `M0,26 C-18,${f(34 + sw * .3)} ${f(-34 + sw)},${f(18 + sw * .4)} ${f(-30 + sw)},${f(-4 + sw * .2)} C${f(-28 + sw)},-16 ${f(-18 + sw * .6)},-18 ${f(-16 + sw * .5)},-10`);
      const s45 = Math.sin(t * 0.45), look = s45 > 0.55 ? -16 : s45 < -0.7 ? 10 : 0;
      headR += (look - headR) * 0.05;
      head.setAttribute('transform', `rotate(${headR.toFixed(2)} 12 -6)`);
      // Birds loop across the sky
      for (const b of birds) {
        const p = (((t - b.start) / b.dur) % 1 + 1) % 1;
        b.el.setAttribute('d', birdPath(-80 + p * 1180 + b.dx, b.y - p * 45 + Math.sin(t * 1.2 + b.phase) * 4, b.scale * 1.35, Math.sin(t * 9 + b.phase)));
      }
      for (const r of rays) {
        const a = (r.a + t * 2) * Math.PI / 180, L = 900;
        const x1 = sunPos.x + Math.cos(a - r.w / 200) * L, y1 = sunPos.y + Math.sin(a - r.w / 200) * L;
        const x2 = sunPos.x + Math.cos(a + r.w / 200) * L, y2 = sunPos.y + Math.sin(a + r.w / 200) * L;
        r.el.setAttribute('d', `M${f(sunPos.x)},${f(sunPos.y)} L${f(x1)},${f(y1)} L${f(x2)},${f(y2)} Z`);
        r.el.setAttribute('opacity', (0.35 + 0.35 * Math.sin(t * .7 + r.a)).toFixed(2));
      }
      for (const d of dust) {
        d.y -= d.v * .4; d.x += Math.sin(t * .5 + d.p) * .25; if (d.y < 160) { d.y = 600; d.x = Math.random() * W; }
        d.el.setAttribute('cx', f(d.x)); d.el.setAttribute('cy', f(d.y)); d.el.setAttribute('r', d.r);
        d.el.setAttribute('opacity', dustOn ? (0.25 + 0.5 * Math.abs(Math.sin(t + d.p))).toFixed(2) : 0);
      }
      $('c1').setAttribute('transform', `translate(${f(((t * 6) % 1300) - 150)},0)`);
      $('c2').setAttribute('transform', `translate(${f(((t * 4 + 600) % 1300) - 150)},0)`);
      if (light.night > 0.05) starEls.forEach((s, i) => s.setAttribute('opacity', (0.45 + 0.55 * Math.abs(Math.sin(t * 0.8 + i))).toFixed(2)));
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

// Renders "What's new" (data/updates.json) and the ideas roadmap (data/roadmap.json).
(function () {
  const hi = () => document.documentElement.lang === 'hi';
  const esc = (s) => String(s || '').replace(/[&<>"]/g, (c) => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;' }[c]));
  const date = (d) => new Date(d + 'T00:00:00').toLocaleDateString(hi() ? 'hi-IN' : 'en-IN', { day: 'numeric', month: 'long', year: 'numeric' });
  let updates = null, roadmap = null;

  async function load(url) { try { const r = await fetch(url, { cache: 'no-cache' }); return r.ok ? r.json() : []; } catch (e) { return []; } }

  function renderLatest(el) {
    const u = updates[0]; if (!u) return;
    el.innerHTML = u.highlights.map((h) => `<div class="card reveal in"><div class="ic">${h.icon}</div><span class="badge new">v${esc(u.version)}</span><h3 style="margin-top:8px">${esc(hi() && h.titleHi ? h.titleHi : h.title)}</h3><p>${esc(h.text)}</p></div>`).join('');
  }
  function renderAll(el) {
    el.innerHTML = updates.map((u, i) => `
      <article class="update reveal in" id="v${esc(u.version)}">
        <div class="ver"><span class="badge ${i === 0 ? 'new' : ''}">${hi() ? 'संस्करण' : 'Version'} ${esc(u.version)}</span><span style="color:var(--muted);font-size:.9rem">${date(u.date)}</span></div>
        <h3>${esc(hi() && u.titleHi ? u.titleHi : u.title)}</h3>
        <div class="grid" style="margin:16px 0">${u.highlights.map((h) => `<div class="card" style="box-shadow:none"><div class="ic">${h.icon}</div><h3 style="font-size:1.1rem">${esc(hi() && h.titleHi ? h.titleHi : h.title)}</h3><p>${esc(h.text)}</p></div>`).join('')}</div>
        ${u.more && u.more.length ? `<ul>${u.more.map((m) => `<li>${esc(m)}</li>`).join('')}</ul>` : ''}
        ${u.screens && u.screens.length ? `<div style="display:flex;gap:14px;margin-top:16px;flex-wrap:wrap">${u.screens.map((s) => `<div class="phone" style="width:150px;padding:6px;border-radius:24px"><img src="/img/screens/${esc(s)}.webp" alt="" loading="lazy" style="border-radius:19px"></div>`).join('')}</div>` : ''}
      </article>`).join('');
  }
  function renderRoadmap(el) {
    const label = { planned: hi() ? 'योजना में' : 'Planned', progress: hi() ? 'प्रगति पर' : 'In progress', done: hi() ? 'पूर्ण' : 'Done' };
    el.innerHTML = ['progress', 'planned', 'done'].map((st) => `<div><h3 style="margin-bottom:12px">${label[st]}</h3>${roadmap.filter((r) => r.status === st).map((r) => `<div class="card" style="margin-bottom:12px"><span class="badge ${st}">${label[st]}</span><h3 style="font-size:1.12rem;margin-top:8px">${esc(hi() && r.titleHi ? r.titleHi : r.title)}</h3><p>${esc(r.text)}</p></div>`).join('')}</div>`).join('');
  }

  async function run() {
    const latest = document.querySelector('[data-latest-updates]'), all = document.querySelector('[data-updates]'), rm = document.querySelector('[data-roadmap]');
    if (latest || all) { updates = updates || await load('/data/updates.json'); latest && renderLatest(latest); all && renderAll(all); }
    if (rm) { roadmap = roadmap || await load('/data/roadmap.json'); renderRoadmap(rm); }
  }
  run();
  document.addEventListener('langchange', run);
})();

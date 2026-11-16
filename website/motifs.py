"""Original glowing line-art motifs (SVG) used as slow animated section backgrounds.

Use in a page fragment as {{motif:NAME POSITION}}, e.g. {{motif:shiva right}}.
All art is drawn here from simple strokes; no copied artwork.
"""


def _beads(cx, cy, r, n):
    out = []
    for i in range(n):
        import math
        a = 2 * math.pi * i / n - math.pi / 2
        x, y = cx + r * math.cos(a), cy + r * math.sin(a)
        out.append(f'<circle class="draw m-bead" cx="{x:.1f}" cy="{y:.1f}" r="4.2" style="animation-delay:{i * 8 / n:.2f}s"/>')
    return "".join(out)


def _ticks(cx, cy, r1, r2, n):
    import math
    out = []
    for i in range(n):
        a = 2 * math.pi * i / n
        out.append(f'<path class="draw" d="M{cx + r1 * math.cos(a):.1f},{cy + r1 * math.sin(a):.1f} L{cx + r2 * math.cos(a):.1f},{cy + r2 * math.sin(a):.1f}"/>')
    return "".join(out)


def _petals(cx, cy, n, length, spread):
    out = []
    for i in range(n):
        ang = -spread / 2 + spread * i / (n - 1)
        out.append(f'<path class="draw m-petal" style="--r:{ang:.0f}deg;animation-delay:{i * .25:.2f}s" '
                   f'd="M{cx},{cy} C{cx - 14},{cy - length * .5} {cx - 6},{cy - length} {cx},{cy - length} C{cx + 6},{cy - length} {cx + 14},{cy - length * .5} {cx},{cy}"/>')
    return "".join(out)


TRISHUL = ('<path class="draw" d="M100,190 L100,40"/>'
           '<path class="draw" d="M100,40 L92,58 M100,40 L108,58"/>'
           '<path class="draw" d="M70,40 C70,70 82,82 100,84 C118,82 130,70 130,40"/>'
           '<path class="draw" d="M70,40 L64,54 M70,40 L78,52 M130,40 L136,54 M130,40 L122,52"/>')

MOTIFS = {
    "om": f'''<svg viewBox="0 0 200 200"><g class="m-rotate">
      <circle class="draw" cx="100" cy="100" r="92"/><circle class="draw gold" cx="100" cy="100" r="80" stroke-dasharray="3 7"/>
      {_ticks(100, 100, 84, 92, 36)}</g>
      <text x="100" y="138" text-anchor="middle" font-size="110" font-family="Noto Sans Devanagari, sans-serif" class="draw" fill="none" stroke="currentColor" stroke-width="1.6">ॐ</text></svg>''',

    "shiva": f'''<svg viewBox="0 0 200 220">
      <path class="draw gold" d="M130,22 A26,26 0 1 0 156,52 A20,20 0 1 1 130,22 Z"/>
      <g transform="translate(-10,6)">{TRISHUL}
      <path class="draw" d="M86,104 L114,104 L86,128 L114,128 Z"/><path class="draw" d="M86,116 C74,124 70,138 76,146 M114,116 C126,122 130,136 124,142"/>
      <circle class="draw" cx="76" cy="148" r="3"/><circle class="draw" cx="124" cy="144" r="3"/></g>
      <path class="draw m-flow" d="M150,70 C120,95 170,120 140,148 C110,176 160,196 130,216"/>
      <path class="draw m-flow" style="animation-delay:-2s" d="M164,74 C134,99 184,124 154,152 C124,180 174,200 144,220"/></svg>''',

    "panchang": f'''<svg viewBox="0 0 200 200">
      <g class="m-rotate">{_ticks(100, 100, 78, 92, 27)}<circle class="draw" cx="100" cy="100" r="92"/><circle class="draw" cx="100" cy="100" r="78"/></g>
      <g class="m-rotate-rev">{_ticks(100, 100, 56, 70, 12)}<circle class="draw gold" cx="100" cy="100" r="70"/><circle class="draw gold" cx="100" cy="100" r="56"/></g>
      <circle class="draw" cx="86" cy="96" r="16"/>{_ticks(86, 96, 20, 28, 12)}
      <path class="draw gold" d="M124,90 A14,14 0 1 0 134,114 A11,11 0 1 1 124,90 Z"/>
      <path class="draw m-flow" d="M20,150 Q100,40 180,150"/></svg>''',

    "mala": f'''<svg viewBox="0 0 200 200">{_beads(100, 92, 72, 27)}
      <circle class="draw gold" cx="100" cy="172" r="7"/><path class="draw gold" d="M100,179 L94,198 M100,179 L100,200 M100,179 L106,198"/></svg>''',

    "lotus": f'''<svg viewBox="0 0 200 160"><g>{_petals(100, 130, 7, 90, 110)}</g>
      <path class="draw" d="M30,132 Q100,150 170,132"/><path class="draw m-flow" d="M10,148 Q55,138 100,148 T190,148"/></svg>''',

    "temple": '''<svg viewBox="0 0 220 200">
      <path class="draw" d="M40,170 C40,120 52,92 66,70 L78,70 C92,92 104,120 104,170 Z"/><ellipse class="draw" cx="72" cy="66" rx="12" ry="5"/><path class="draw" d="M72,61 L72,48"/>
      <path class="draw" d="M110,170 C110,105 128,70 146,42 L158,42 C176,70 194,105 194,170 Z"/><ellipse class="draw gold" cx="152" cy="38" rx="15" ry="6"/>
      <path class="draw" d="M152,32 L152,12"/><path class="draw gold" d="M152,12 L176,16 L152,22"/>
      <path class="draw" d="M30,170 L204,170 M24,178 L210,178"/>
      <g class="m-swing" style="transform-origin:96px 30px"><path class="draw" d="M96,30 L96,46"/><path class="draw gold" d="M84,74 C84,56 88,46 96,46 C104,46 108,56 108,74 Z"/><circle class="draw" cx="96" cy="78" r="3"/></g></svg>''',

    "scripture": '''<svg viewBox="0 0 220 200">
      <g class="m-float">
      <rect class="draw" x="20" y="60" width="180" height="22" rx="8"/><rect class="draw" x="20" y="88" width="180" height="22" rx="8"/><rect class="draw gold" x="20" y="116" width="180" height="22" rx="8"/>
      <circle class="draw" cx="60" cy="99" r="3"/><circle class="draw" cx="160" cy="99" r="3"/>
      <path class="draw" d="M40,69 L180,69 M40,74 L150,74 M40,97 L52,97 M68,97 L152,97 M168,97 L180,97 M40,125 L180,125 M40,130 L120,130"/></g>
      <path class="draw" d="M30,40 L190,24"/><circle class="draw gold" cx="40" cy="44" r="14"/><circle class="draw gold" cx="176" cy="30" r="10"/>
      <path class="draw" d="M50,34 L186,19 M50,38 L186,23 M50,42 L186,27"/></svg>''',

    "mahakaal": '''<svg viewBox="0 0 200 220">
      <path class="draw m-smoke" d="M100,70 C90,50 112,40 100,20 C92,8 104,2 100,-10"/>
      <path class="draw m-smoke" style="animation-delay:-2.3s" d="M88,74 C76,54 96,44 84,22"/>
      <path class="draw m-smoke" style="animation-delay:-4.6s" d="M112,74 C124,54 104,44 118,24"/>
      <path class="draw" d="M70,170 C70,110 78,80 100,80 C122,80 130,110 130,170"/>
      <path class="draw gold" d="M80,118 L120,118 M78,128 L122,128 M80,138 L120,138"/><circle class="draw" cx="100" cy="128" r="4"/>
      <path class="draw" d="M30,170 C30,190 170,190 170,170 L192,176 C196,180 190,186 180,184 L170,182"/>
      <path class="draw" d="M30,170 L170,170"/><path class="draw" d="M40,196 L160,196"/>
      <path class="draw gold" d="M90,92 A10,10 0 1 0 104,104 A8,8 0 1 1 90,92 Z"/></svg>''',

    "nandi": '''<svg viewBox="0 0 240 180"><g class="m-breathe">
      <path class="draw" d="M30,150 C26,120 40,96 70,92 C84,78 110,74 124,88 C150,86 170,92 178,108"/>
      <path class="draw" d="M178,108 C186,92 196,82 208,82 C220,82 226,92 222,104 C218,116 208,122 198,124 C192,140 180,150 160,152 L30,152"/>
      <path class="draw gold" d="M198,84 C194,70 200,62 208,60 M214,84 C220,72 230,70 236,74"/>
      <circle class="draw" cx="214" cy="96" r="2"/><path class="draw" d="M210,112 Q216,116 222,110"/>
      <path class="draw" d="M70,152 C68,140 80,134 92,140 M130,152 C128,138 146,134 152,146"/>
      <path class="draw gold" d="M176,118 Q170,128 178,136 M168,124 Q164,132 170,138"/>
      <path class="draw" d="M30,140 C14,138 10,124 18,116"/></g>
      <path class="draw" d="M20,166 L230,166"/></svg>''',

    "ganesha": '''<svg viewBox="0 0 200 200">
      <path class="draw gold" d="M78,40 L100,22 L122,40"/><path class="draw gold" d="M80,40 L120,40"/>
      <circle class="draw" cx="100" cy="72" r="32"/>
      <path class="draw" d="M68,62 C40,46 26,74 38,96 C46,110 62,104 70,92"/><path class="draw" d="M132,62 C160,46 174,74 162,96 C154,110 138,104 130,92"/>
      <path class="draw" d="M100,90 C100,118 92,136 106,148 C118,158 132,148 126,138"/>
      <path class="draw gold" d="M92,62 L92,68 M108,62 L108,68"/><path class="draw" d="M86,92 L80,104"/>
      <circle class="draw gold" cx="150" cy="160" r="12"/><path class="draw gold" d="M140,156 Q150,140 160,156"/></svg>''',

    "hanuman": '''<svg viewBox="0 0 200 220">
      <circle class="draw gold" cx="100" cy="64" r="40"/>
      <path class="draw" d="M62,52 Q100,44 138,52 M60,70 Q100,62 140,70 M66,86 Q100,80 134,86"/>
      <path class="draw" d="M100,24 L100,12 M94,14 L106,14"/>
      <path class="draw" d="M94,104 L94,190 M106,104 L106,190"/><path class="draw gold" d="M88,190 L112,190 L106,206 L94,206 Z"/>
      <path class="draw m-flow" d="M150,40 C170,60 166,90 150,110"/></svg>''',

    "krishna": '''<svg viewBox="0 0 220 200">
      <g transform="rotate(-18 110 120)"><rect class="draw" x="20" y="112" width="180" height="14" rx="7"/>
      <circle class="draw" cx="60" cy="119" r="2.5"/><circle class="draw" cx="78" cy="119" r="2.5"/><circle class="draw" cx="96" cy="119" r="2.5"/><circle class="draw" cx="114" cy="119" r="2.5"/><circle class="draw" cx="132" cy="119" r="2.5"/>
      <path class="draw gold" d="M160,126 C162,146 150,156 140,166 M166,126 C170,146 164,160 156,172"/></g>
      <g class="m-swing" style="transform-origin:160px 196px">
      <path class="draw" d="M160,196 C150,150 150,90 166,40"/>
      <ellipse class="draw gold" cx="168" cy="46" rx="18" ry="26"/><ellipse class="draw" cx="168" cy="50" rx="10" ry="15"/><ellipse class="draw gold" cx="168" cy="54" rx="4" ry="6"/>
      <path class="draw" d="M150,80 L130,70 M152,96 L128,90 M154,112 L130,108 M170,82 L190,74 M170,98 L192,94 M168,114 L190,112"/></g></svg>''',

    "devi": f'''<svg viewBox="0 0 200 220"><g transform="translate(0,-10)">{TRISHUL}</g>
      <g transform="translate(0,60)">{_petals(100, 150, 5, 56, 100)}</g>
      <circle class="draw gold" cx="100" cy="74" r="10"/></svg>''',
}


def render(name, pos="right", style=""):
    svg = MOTIFS[name]
    st = f' style="{style}"' if style else ""
    return f'<div class="motif {pos}" aria-hidden="true"{st}>{svg}</div>'

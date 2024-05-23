/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}', './node_modules/flowbite/**/*.js'],
  theme: {
    extend: {
      colors: {
        primary: {
          50: '#fbfefb',
          100: '#effcee',
          200: '#daf9d8',
          300: '#bef4ba',
          400: '#99ed93',
          500: '#6ce664',
          600: '#37dd2b',
          700: '#23a21a',
          800: '#156010',
          900: '#0c3909',
          950: '#0a2c07'
        }
      }
    }
  },
  plugins: [
    'flowbite/plugin',
    function ({ addUtilities }) {
      addUtilities(
        {
          '.pause-marquee': {
            'animation-play-state': 'paused'
          }
        },
        ['responsive', 'hover']
      )
    }
  ]
}

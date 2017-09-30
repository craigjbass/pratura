module.exports = {
    components: 'src/components/**/*.{ts,tsx}',
    propsParser: require('react-docgen-typescript').parse,
    webpackConfig: require('./config/webpack.config.dev.js')
}

const HtmlWebpackPlugin = require("html-webpack-plugin");
const path = require("path");

module.exports = {
  entry: "./src/index.js",
  output: {
    filename: "bundle.[hash].js",
    path: path.resolve(__dirname, "dist"),
    publicPath: '/',  // Ajoutez cette ligne
},
  plugins: [
    new HtmlWebpackPlugin({
      template: "./src/index.html",
    }),
  ],
  resolve: {
    modules: [__dirname, "src", "node_modules"],
    extensions: ["*", ".js", ".jsx", ".tsx", ".ts"],
  },
  module: {
    rules: [
      {
        test: /\.jsx?$/,
        exclude: /node_modules/,
        loader: require.resolve("babel-loader"),
      },
      {
        test: /\.css$/,
        use: ["style-loader", "css-loader"],
      },
      {
        test: /\.png|svg|jpg|gif$/,
        use: ["file-loader"],
      }, 
      {
        test: /\.s[ac]ss$/i,
        use: [
          // Crée des noeuds `style` à partir des chaînes de caractères JS
          "style-loader",
          // Traduit le CSS en chaînes de caractères CommonJS
          "css-loader",
          // Compile Sass en CSS
          "sass-loader",
        ],
      }
    ],
  },
  devServer: {
    historyApiFallback: true, // Ajout pour gérer l'historique HTML5
    static: {
      directory: path.join(__dirname, 'dist'), // Dossier où se trouvent vos fichiers statiques compilés
    },
    compress: true, // Pour tout compression de fichiers, utile en prod
    port: 3000, // Vous pouvez spécifier le port que vous voulez utiliser
    open: true, // Pour ouvrir automatiquement le navigateur
  },
};
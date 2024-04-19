import React, { createContext } from "react";
import ReactDOM from "react-dom/client";
import App from "./components/App";
import "./styles/style.scss";
import "./styles/bootstrap.min.css";

export const myContext = createContext();

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <App/>
  </React.StrictMode>
);
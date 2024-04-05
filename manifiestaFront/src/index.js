import React, { createContext } from "react";
import ReactDOM from "react-dom/client";
import App from "./components/App";


export const myContext = createContext();

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <App/>
  </React.StrictMode>
);
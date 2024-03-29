import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./styles.css";

import logoImage from "../../assets/logobook.svg";
import api from "../../services/api";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  async function login(event) {
    event.preventDefault();

    const data = {
      username,
      password,
    };

    try {
      const response = await api.post("/auth/signin", data);

      localStorage.setItem("username", username);
      localStorage.setItem("accessToken", response.data.accessToken);

      navigate("/books");
    } catch (err) {
      console.log("Erro - ", err);
      alert("Login failed, Try again");
    }
  }

  return (
    <div className="login-container">
      <section className="form">
        <form onSubmit={login}>
          <h1>Access your Account</h1>
          <input
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button className="button" type="submit">
            Login
          </button>
        </form>
      </section>
    </div>
  );
}

import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import api from "../../services/api";

import { FiArrowLeft } from "react-icons/fi";

import logoImage from "../../assets/logo.svg";

import "./styles.css";

export default function NewBook() {
  const [id, setId] = useState(null);
  const [author, setAuthor] = useState("");
  const [launchDate, setLaunchDate] = useState("");
  const [price, setPrice] = useState("");
  const [title, setTitle] = useState("");

  const username = localStorage.getItem("username");
  const accessToken = localStorage.getItem("accessToken");
  const navigate = useNavigate();

  async function createNewBook(event) {
    event.preventDefault();

    const data = {
      title,
      author,
      launchDate,
      price,
    };

    try {
      await api.post("/api/books/v1", data, {
        headers: { Authorization: `Bearer ${accessToken}` },
      });
      navigate("/books");
    } catch (error) {
      console.log("Erro: ", error);
      alert("Error while recording book. Try again");
    }
  }

  return (
    <div className="new-book-container">
      <div className="content">
        <section className="form">
          <img src={logoImage} />
          <h1>Add New Book</h1>
          <p>Enter the book information and click on 'Add'</p>
          <Link className="back-link" to="/books">
            <FiArrowLeft size={16} color="#251fc5">
              Home
            </FiArrowLeft>
          </Link>
        </section>
        <form onSubmit={createNewBook}>
          <input
            placeholder="Title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
          <input
            placeholder="Author"
            value={author}
            onChange={(e) => setAuthor(e.target.value)}
          />
          <input
            type="date"
            value={launchDate}
            onChange={(e) => setLaunchDate(e.target.value)}
          />
          <input
            placeholder="Price"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
          />
          <button className="button" type="submit">
            Add
          </button>
        </form>
      </div>
    </div>
  );
}

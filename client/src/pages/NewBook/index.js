import React, { useState, useEffect } from "react";
import { useNavigate, Link, useParams } from "react-router-dom";
import api from "../../services/api";

import { FiArrowLeft } from "react-icons/fi";

import logoImage from "../../assets/logobook.svg";

import "./styles.css";

export default function NewBook() {
  const [id, setId] = useState(null);
  const [author, setAuthor] = useState("");
  const [launchDate, setLaunchDate] = useState("");
  const [price, setPrice] = useState("");
  const [title, setTitle] = useState("");
  const { bookId } = useParams();

  const accessToken = localStorage.getItem("accessToken");

  const navigate = useNavigate();

  async function loadBook() {
    try {
      const response = await api.get(`api/books/v1/${bookId}`, {
        headers: { Authorization: `Bearer ${accessToken}` },
      });

      let adjustedDate = response.data.launchDate.split("T", 10)[0];

      setId(response.data.id);
      setTitle(response.data.title);
      setAuthor(response.data.author);
      setPrice(response.data.price);
      setLaunchDate(adjustedDate);
    } catch (error) {
      alert("Error recovering Book! Tray again");
      navigate("/books");
    }
  }

  useEffect(() => {
    if (bookId === "0") {
      console.log("bookId: ", bookId);
      return;
    } else {
      loadBook();
    }
  }, [bookId]);

  async function saveOrUpdate(event) {
    event.preventDefault();

    const data = {
      title,
      author,
      launchDate,
      price,
    };

    console.log("BookId: ", bookId);

    try {
      if (bookId === "0") {
        await api.post("/api/books/v1", data, {
          headers: { Authorization: `Bearer ${accessToken}` },
        });
      } else {
        data.id = bookId;
        await api.put("/api/books/v1", data, {
          headers: { Authorization: `Bearer ${accessToken}` },
        });
      }

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
          <h1>{bookId === "0" ? "Add New" : "Update new Book"}</h1>
          <p>
            Enter the book information and click on{" "}
            {bookId === "0" ? "'Add'" : "'Update'"}
          </p>
          <Link className="back-link" to="/books">
            <FiArrowLeft size={16} color="#251fc5" />
            Back to books
          </Link>
        </section>
        <form onSubmit={saveOrUpdate}>
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
            {bookId === "0" ? "Add" : "Update"}
          </button>
        </form>
      </div>
    </div>
  );
}

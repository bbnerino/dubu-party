// import { axios } from "axios";
const { BASE_FETCH_URL } = process.env;

export const MainAPI = {
  getArticles: async () => {
    try {
      const res = await fetch(`http://localhost:3000/api/articles/`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (res.status === 200) {
        const data = await res.json();
        return data;
      }
    } catch (error) {
      console.error("error");
      return [];
    }
  },
};

import { useState, useEffect } from "react";
import {
  PieChart,
  Pie,
  Cell,
  Tooltip,
} from "recharts";

function Dashboard() {
  const [result, setResult] = useState("");
  const [logs, setLogs] = useState([]);
  const [autoRun, setAutoRun] = useState(true);
  const [alert, setAlert] = useState("");

  const [stats, setStats] = useState({
    delayed: 0,
    stuck: 0,
    priority: 0,
  });

  const runAgent = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/agent/run", {
        method: "POST",
      });

      const text = await res.text();
      setResult(text);

      fetchLogs();
    } catch {
      setResult("Error running agent");
    }
  };

  const fetchLogs = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/agent/logs");
      const data = await res.json();

      const map = {};
      data.forEach((log) => {
        const existing = map[log.orderId];
        if (
          !existing ||
          new Date(log.timestamp) > new Date(existing.timestamp)
        ) {
          map[log.orderId] = log;
        }
      });

      const latestLogs = Object.values(map);
      setLogs(latestLogs);

      let delayed = 0,
        stuck = 0,
        priority = 0;

      latestLogs.forEach((log) => {
        if (log.decision?.includes("escalate")) delayed++;
        if (log.decision?.includes("reorder")) stuck++;
        if (log.decision?.includes("priority")) priority++;
      });

      setStats({ delayed, stuck, priority });

      
      if (delayed > 0) {
        setAlert(" Critical delays detected!");
      } else {
        setAlert("");
      }

    } catch {
      console.log("error");
    }
  };

  useEffect(() => {
    if (!autoRun) return;
    const interval = setInterval(runAgent, 8000);
    return () => clearInterval(interval);
  }, [autoRun]);

  const chartData = [
    { name: "Delayed", value: stats.delayed },
    { name: "Stuck", value: stats.stuck },
    { name: "Priority", value: stats.priority },
  ];

  const COLORS = ["#ff4d4f", "#faad14", "#1890ff"];

  return (
    <div
      style={{
        minHeight: "100vh",
        backgroundImage:
          "url('https://images.unsplash.com/photo-1605902711622-cfb43c4437b5')",
        backgroundSize: "cover",
        padding: "40px",
        color: "white",
      }}
    >
      <div style={{ maxWidth: "1100px", margin: "0 auto" }}>

        
        {alert && (
          <div style={{
            background: "#ff4d4f",
            padding: "10px",
            borderRadius: "8px",
            textAlign: "center",
            marginBottom: "15px",
            fontWeight: "bold",
            boxShadow: "0 0 15px red"
          }}>
            {alert}
          </div>
        )}

        
        <div style={{
          backdropFilter: "blur(15px)",
          background: "rgba(0,0,0,0.6)",
          padding: "20px",
          borderRadius: "12px",
          textAlign: "center"
        }}>
          <h1>SH's AI Control Tower</h1>
        </div>

        
        <div style={{ textAlign: "center", marginTop: "20px" }}>
          <button onClick={runAgent}>Run AI</button>
          <button onClick={() => setAutoRun(!autoRun)} style={{ marginLeft: "10px" }}>
            {autoRun ? "Stop Auto" : "Start Auto"}
          </button>
        </div>

        
        <div style={{
          display: "flex",
          justifyContent: "center",
          marginTop: "30px"
        }}>
          <PieChart width={300} height={300}>
            <Pie
              data={chartData}
              dataKey="value"
              outerRadius={100}
              label
            >
              {chartData.map((entry, index) => (
                <Cell key={index} fill={COLORS[index]} />
              ))}
            </Pie>
            <Tooltip />
          </PieChart>
        </div>

        
        <div style={{
          maxWidth: "800px",
          margin: "30px auto",
          background: "black",
          color: "#00ff9c",
          padding: "20px",
          borderRadius: "10px",
          fontFamily: "monospace"
        }}>
          {result}
        </div>

        
        <div style={{
          backdropFilter: "blur(10px)",
          background: "rgba(0,0,0,0.6)",
          padding: "15px",
          borderRadius: "10px",
          marginTop: "20px"
        }}>
          <h3> AI Reasoning</h3>
          <p>
            Decisions are based on order delay, supplier lead time,
            and quantity thresholds. The system automatically optimizes
            supply chain flow using rule-based + AI fallback logic.
          </p>
        </div>

        
        <div style={{ marginTop: "30px" }}>
          <h2>Latest Decisions</h2>

          {logs.map((log) => (
            <div key={log.orderId} style={{
              background: "rgba(0,0,0,0.6)",
              padding: "15px",
              borderRadius: "10px",
              marginTop: "10px"
            }}>
              <h3>Order #{log.orderId}</h3>
              <p>{log.decision}</p>
              <p>{log.action}</p>
            </div>
          ))}
        </div>

      </div>
    </div>
  );
}

export default Dashboard;
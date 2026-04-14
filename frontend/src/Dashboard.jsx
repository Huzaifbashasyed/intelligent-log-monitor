import React, { useEffect, useState } from "react";
import axios from "axios";
import {
    LineChart,
    Line,
    XAxis,
    YAxis,
    Tooltip,
    CartesianGrid,
    ResponsiveContainer
} from "recharts";

function Dashboard() {
    const [logs, setLogs] = useState([]);
    const [filter, setFilter] = useState("ALL");

    // 🔄 Auto refresh every 5 sec
    useEffect(() => {
        fetchLogs();
        const interval = setInterval(fetchLogs, 5000);
        return () => clearInterval(interval);
    }, []);

    const fetchLogs = () => {
        axios.get("http://localhost:8080/api/logs")
            .then(res => setLogs(res.data))
            .catch(err => console.error(err));
    };

    // 🎯 Filter logs
    const filteredLogs = filter === "ALL"
        ? logs
        : logs.filter(log => log.level === filter);

    // 💎 Metrics
    const totalLogs = logs.length;
    const errorLogs = logs.filter(l => l.level === "ERROR").length;
    const warnLogs = logs.filter(l => l.level === "WARN").length;

    return (
        <div style={{ padding: "20px", color: "white" }}>
            <h1>📊 Log Monitoring Dashboard</h1>

            {/* 💎 CARDS */}
            <div style={{ display: "flex", gap: "20px", marginBottom: "20px" }}>
                <div style={cardStyle}>Total Logs: {totalLogs}</div>
                <div style={{ ...cardStyle, background: "#ff4d4d" }}>
                    Errors: {errorLogs}
                </div>
                <div style={{ ...cardStyle, background: "#ffa500" }}>
                    Warnings: {warnLogs}
                </div>
            </div>

            {/* 🎯 FILTER */}
            <div style={{ marginBottom: "15px" }}>
                <button onClick={() => setFilter("ALL")}>All</button>
                <button onClick={() => setFilter("ERROR")}>Error</button>
                <button onClick={() => setFilter("WARN")}>Warn</button>
                <button onClick={() => setFilter("INFO")}>Info</button>
            </div>

            {/* 📊 CHART */}
            <div style={{ width: "100%", height: 300 }}>
                <ResponsiveContainer>
                    <LineChart data={filteredLogs}>
                        <XAxis
                            dataKey="timestamp"
                            tickFormatter={(t) => t.split("T")[1]}
                        />
                        <YAxis />
                        <Tooltip />
                        <CartesianGrid strokeDasharray="3 3" />
                        <Line type="monotone" dataKey="durationMs" stroke="#00ffcc" />
                    </LineChart>
                </ResponsiveContainer>
            </div>

            {/* 📜 TABLE */}
            <h2>📜 Logs</h2>
            <table border="1" width="100%" style={{ marginTop: "10px" }}>
                <thead>
                <tr>
                    <th>Time</th>
                    <th>Service</th>
                    <th>Level</th>
                    <th>Message</th>
                    <th>Duration</th>
                </tr>
                </thead>
                <tbody>
                {filteredLogs.map((log, i) => (
                    <tr key={i}>
                        <td>{log.timestamp}</td>
                        <td>{log.service}</td>
                        <td style={{ color: getColor(log.level) }}>
                            {log.level}
                        </td>
                        <td>{log.message}</td>
                        <td>{log.durationMs}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

// 💎 Card Style
const cardStyle = {
    padding: "15px",
    background: "#222",
    borderRadius: "10px",
    flex: 1,
    textAlign: "center",
    fontSize: "18px"
};

// 🎨 Level colors
const getColor = (level) => {
    if (level === "ERROR") return "red";
    if (level === "WARN") return "orange";
    return "lightgreen";
};

export default Dashboard;
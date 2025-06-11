import React, { useState } from 'react';
import axios from 'axios';

const BASE_URL = 'http://localhost:9091';

const backgroundStyle = {
  backgroundImage: `url('https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=1950&q=80')`,
  backgroundSize: 'cover',
  backgroundPosition: 'center',
  minHeight: '100vh',
  padding: '40px',
};

const HotelActions = () => {
  const [hotels, setHotels] = useState([]);
  const [hotelId, setHotelId] = useState('');
  const [cityId, setCityId] = useState('');
  const [message, setMessage] = useState('');
  const [searchHotelId, setSearchHotelId] = useState('');

  const getHotelById = async () => {
    try {
      const res = await axios.get(`${BASE_URL}/hotel/${searchHotelId}`);
      setHotels([res.data]);
      setMessage('');
    } catch (err) {
      setHotels([]);
      setMessage(`❌ Hotel not found with ID ${searchHotelId}`);
    }
  };

  const deleteHotel = async () => {
    await axios.delete(`${BASE_URL}/hotel/${hotelId}`);
    setMessage(`✅ Hotel with ID ${hotelId} deleted.`);
    setHotels([]);
  };

  const getTop3Hotels = async () => {
    const res = await axios.get(`${BASE_URL}/search/${cityId}?sortBy=distance`);
    setHotels(res.data.slice(0, 3));
    setMessage('');
  };

  return (
    <div style={backgroundStyle}>
      <div style={glassCard}>
        <div style={{ fontFamily: 'Arial, sans-serif', padding: '40px' }}>
          <h1 style={{ textAlign: 'center', color: '#2c3e50' }}>🏨 Hotel Booking Dashboard</h1>

          <div style={cardStyle}>
            <input
              placeholder="Hotel ID to search"
              value={searchHotelId}
              onChange={(e) => setSearchHotelId(e.target.value)}
              style={inputStyle}
            />
            <button onClick={getHotelById} style={btnStyle}>Get Hotel</button>
          </div>

          <div style={cardStyle}>
            <input
              placeholder="Hotel ID to delete"
              value={hotelId}
              onChange={(e) => setHotelId(e.target.value)}
              style={inputStyle}
            />
            <button onClick={deleteHotel} style={btnStyle}>Delete Hotel</button>
          </div>

          <div style={cardStyle}>
            <input
              placeholder="City ID for nearest hotels"
              value={cityId}
              onChange={(e) => setCityId(e.target.value)}
              style={inputStyle}
            />
            <button onClick={getTop3Hotels} style={btnStyle}>Top 3 Nearest Hotels</button>
          </div>

          {message && <p style={{ color: 'green', fontWeight: 'bold' }}>{message}</p>}

          {hotels.length > 0 && (
            <table style={tableStyle}>
              <thead>
                <tr>
                  <th style={headerCellStyle}>Hotel Name</th>
                  <th style={headerCellStyle}>Address</th>
                  <th style={headerCellStyle}>Rating</th>
                  <th style={headerCellStyle}>Latitude</th>
                  <th style={headerCellStyle}>Longitude</th>
                  <th style={headerCellStyle}>City</th>
                  <th style={headerCellStyle}>City Centre Lat</th>
                  <th style={headerCellStyle}>City Centre Lng</th>
                </tr>
              </thead>
              <tbody>
                {hotels.map((hotel) => (
                  <tr key={hotel.id}>
                    <td style={cellStyle}>{hotel.name}</td>
                    <td style={cellStyle}>{hotel.address}</td>
                    <td style={cellStyle}>{hotel.rating}</td>
                    <td style={cellStyle}>{hotel.latitude}</td>
                    <td style={cellStyle}>{hotel.longitude}</td>
                    <td style={cellStyle}>{hotel.city?.name}</td>
                    <td style={cellStyle}>{hotel.city?.cityCentreLatitude}</td>
                    <td style={cellStyle}>{hotel.city?.cityCentreLongitude}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      </div>
    </div>
  );
};

const btnStyle = {
  padding: '10px 20px',
  backgroundColor: '#3498db',
  color: '#fff',
  border: 'none',
  borderRadius: '6px',
  cursor: 'pointer',
};

const glassCard = {
  backgroundColor: 'rgba(255, 255, 255, 0.85)',
  borderRadius: '12px',
  padding: '30px',
  maxWidth: '1200px',
  margin: 'auto',
  boxShadow: '0 8px 32px rgba(0, 0, 0, 0.2)',
};

const inputStyle = {
  padding: '10px',
  width: '200px',
  borderRadius: '4px',
  border: '1px solid #ccc',
};

const cardStyle = {
  display: 'flex',
  justifyContent: 'center',
  gap: '10px',
  marginBottom: '20px',
};

const tableStyle = {
  width: '100%',
  borderCollapse: 'collapse',
  marginTop: '30px',
  fontSize: '16px',
  backgroundColor: '#1e1e2f',
  color: '#f1f1f1',
  border: '1px solid #444',
};

const cellStyle = {
  border: '1px solid #444',
  padding: '10px',
  textAlign: 'left',
};

const headerCellStyle = {
  ...cellStyle,
  backgroundColor: '#29293d',
  fontWeight: 'bold',
};

export default HotelActions;

package cinema_backend.api_movie_system.models;

public class SeatLayoutRequest {
    private Integer rows;
    private Integer columns;
    private Integer defaultSeatTypeId;

    public Integer getRows() { return rows; }
    public void setRows(Integer rows) { this.rows = rows; }

    public Integer getColumns() { return columns; }
    public void setColumns(Integer columns) { this.columns = columns; }

    public Integer getDefaultSeatTypeId() { return defaultSeatTypeId; }
    public void setDefaultSeatTypeId(Integer defaultSeatTypeId) { this.defaultSeatTypeId = defaultSeatTypeId; }
}
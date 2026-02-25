using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

#pragma warning disable CA1814 // Prefer jagged arrays over multidimensional

namespace WebStore.Migrations
{
    /// <inheritdoc />
    public partial class InitialWithSeedData : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Categories",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Name = table.Column<string>(type: "nvarchar(50)", maxLength: 50, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Categories", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Articles",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Name = table.Column<string>(type: "nvarchar(100)", maxLength: 100, nullable: false),
                    Price = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    ExpirationDate = table.Column<DateTime>(type: "datetime2", nullable: false),
                    ImageName = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    CategoryId = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Articles", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Articles_Categories_CategoryId",
                        column: x => x.CategoryId,
                        principalTable: "Categories",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.InsertData(
                table: "Categories",
                columns: new[] { "Id", "Name" },
                values: new object[,]
                {
                    { 1, "Elektronika" },
                    { 2, "Spożywcze" },
                    { 3, "Odzież" },
                    { 4, "Chemia" },
                    { 5, "Inne" }
                });

            migrationBuilder.InsertData(
                table: "Articles",
                columns: new[] { "Id", "CategoryId", "ExpirationDate", "ImageName", "Name", "Price" },
                values: new object[,]
                {
                    { 1, 1, new DateTime(2028, 1, 9, 17, 17, 23, 375, DateTimeKind.Local).AddTicks(6337), "laptop.jpg", "Laptop Gamingowy", 4500.00m },
                    { 2, 1, new DateTime(2029, 1, 9, 17, 17, 23, 375, DateTimeKind.Local).AddTicks(6389), "mysz.jpg", "Mysz Bezprzewodowa", 120.00m },
                    { 3, 1, new DateTime(2031, 1, 9, 17, 17, 23, 375, DateTimeKind.Local).AddTicks(6392), "monitor.jpg", "Monitor 24 cale", 650.00m },
                    { 4, 1, new DateTime(2036, 1, 9, 17, 17, 23, 375, DateTimeKind.Local).AddTicks(6394), "hdmi.jpg", "Kabel HDMI", 25.00m },
                    { 5, 2, new DateTime(2026, 1, 16, 17, 17, 23, 375, DateTimeKind.Local).AddTicks(6397), "mleko.jpg", "Mleko 3.2%", 3.50m },
                    { 6, 2, new DateTime(2026, 1, 12, 17, 17, 23, 375, DateTimeKind.Local).AddTicks(6402), "chleb.jpg", "Chleb Pełnoziarnisty", 4.20m },
                    { 7, 2, new DateTime(2027, 1, 9, 17, 17, 23, 375, DateTimeKind.Local).AddTicks(6404), "kawa.jpg", "Kawa Ziarnista 1kg", 55.00m },
                    { 8, 2, new DateTime(2026, 7, 9, 17, 17, 23, 375, DateTimeKind.Local).AddTicks(6408), "czekolada.jpg", "Czekolada Mleczna", 5.00m },
                    { 9, 3, new DateTime(2027, 1, 9, 17, 17, 23, 375, DateTimeKind.Local).AddTicks(6410), "koszulka.jpg", "Koszulka T-Shirt", 45.00m },
                    { 10, 3, new DateTime(2028, 1, 9, 17, 17, 23, 375, DateTimeKind.Local).AddTicks(6412), "jeansy.jpg", "Jeansy Męskie", 150.00m },
                    { 11, 3, new DateTime(2029, 1, 9, 17, 17, 23, 375, DateTimeKind.Local).AddTicks(6415), "kurtka.jpg", "Kurtka Zimowa", 300.00m },
                    { 12, 3, new DateTime(2031, 1, 9, 17, 17, 23, 375, DateTimeKind.Local).AddTicks(6417), "czapka.jpg", "Czapka z daszkiem", 30.00m },
                    { 13, 4, new DateTime(2028, 1, 9, 17, 17, 23, 375, DateTimeKind.Local).AddTicks(6420), "płyn_naczynia.jpg", "Płyn do naczyń", 8.50m },
                    { 14, 4, new DateTime(2027, 1, 9, 17, 17, 23, 375, DateTimeKind.Local).AddTicks(6422), "proszek_pranie.jpg", "Proszek do prania", 45.00m },
                    { 15, 4, new DateTime(2028, 1, 9, 17, 17, 23, 375, DateTimeKind.Local).AddTicks(6424), "mydlo.jpg", "Mydło w płynie", 6.00m },
                    { 16, 5, new DateTime(2031, 1, 9, 17, 17, 23, 375, DateTimeKind.Local).AddTicks(6479), "dlugopisy.jpg", "Zestaw długopisów", 12.00m },
                    { 17, 5, new DateTime(2036, 1, 9, 17, 17, 23, 375, DateTimeKind.Local).AddTicks(6482), "notatnik.jpg", "Notatnik A4", 15.00m },
                    { 18, 5, new DateTime(2030, 1, 9, 17, 17, 23, 375, DateTimeKind.Local).AddTicks(6484), "parasol.jpg", "Parasol", 35.00m }
                });

            migrationBuilder.CreateIndex(
                name: "IX_Articles_CategoryId",
                table: "Articles",
                column: "CategoryId");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Articles");

            migrationBuilder.DropTable(
                name: "Categories");
        }
    }
}

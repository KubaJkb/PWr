using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

#pragma warning disable CA1814 // Prefer jagged arrays over multidimensional

namespace WebStore.Migrations
{
    /// <inheritdoc />
    public partial class SeedProducts : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 1,
                column: "ExpirationDate",
                value: new DateTime(2028, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1707));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 2,
                column: "ExpirationDate",
                value: new DateTime(2029, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1754));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 3,
                column: "ExpirationDate",
                value: new DateTime(2031, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1757));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 4,
                column: "ExpirationDate",
                value: new DateTime(2036, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1759));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 5,
                column: "ExpirationDate",
                value: new DateTime(2026, 1, 16, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1762));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 6,
                column: "ExpirationDate",
                value: new DateTime(2026, 1, 12, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1771));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 7,
                column: "ExpirationDate",
                value: new DateTime(2027, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1773));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 8,
                column: "ExpirationDate",
                value: new DateTime(2026, 7, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1776));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 9,
                column: "ExpirationDate",
                value: new DateTime(2027, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1779));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 10,
                column: "ExpirationDate",
                value: new DateTime(2028, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1782));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 11,
                column: "ExpirationDate",
                value: new DateTime(2029, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1785));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 12,
                column: "ExpirationDate",
                value: new DateTime(2031, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1787));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 13,
                columns: new[] { "ExpirationDate", "ImageName" },
                values: new object[] { new DateTime(2028, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1790), "plyn_naczynia.jpg" });

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 14,
                column: "ExpirationDate",
                value: new DateTime(2027, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1793));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 15,
                column: "ExpirationDate",
                value: new DateTime(2028, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1795));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 16,
                column: "ExpirationDate",
                value: new DateTime(2031, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1798));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 17,
                column: "ExpirationDate",
                value: new DateTime(2036, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1800));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 18,
                column: "ExpirationDate",
                value: new DateTime(2030, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1803));

            migrationBuilder.InsertData(
                table: "Articles",
                columns: new[] { "Id", "CategoryId", "ExpirationDate", "ImageName", "Name", "Price" },
                values: new object[,]
                {
                    { 19, 5, new DateTime(2027, 8, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1880), null, "Towar Testowy nr 19", 20.723321629681m },
                    { 20, 1, new DateTime(2027, 9, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1884), null, "Towar Testowy nr 20", 11.9709161831033m },
                    { 21, 2, new DateTime(2027, 10, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1895), null, "Towar Testowy nr 21", 54.7134516764868m },
                    { 22, 3, new DateTime(2027, 11, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1899), null, "Towar Testowy nr 22", 29.710633844048m },
                    { 23, 4, new DateTime(2027, 12, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1903), null, "Towar Testowy nr 23", 33.0366726604742m },
                    { 24, 5, new DateTime(2028, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1907), null, "Towar Testowy nr 24", 46.7027962802722m },
                    { 25, 1, new DateTime(2028, 2, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1913), null, "Towar Testowy nr 25", 85.7844463537093m },
                    { 26, 2, new DateTime(2028, 3, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1917), null, "Towar Testowy nr 26", 85.5350900960518m },
                    { 27, 3, new DateTime(2028, 4, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1920), null, "Towar Testowy nr 27", 99.2003011059704m },
                    { 28, 4, new DateTime(2028, 5, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1924), null, "Towar Testowy nr 28", 63.7963642320789m },
                    { 29, 5, new DateTime(2028, 6, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1929), null, "Towar Testowy nr 29", 43.7076053168998m },
                    { 30, 1, new DateTime(2028, 7, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1932), null, "Towar Testowy nr 30", 41.0154788628892m },
                    { 31, 2, new DateTime(2028, 8, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1936), null, "Towar Testowy nr 31", 45.1953781986328m },
                    { 32, 3, new DateTime(2028, 9, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1939), null, "Towar Testowy nr 32", 65.7440913003222m },
                    { 33, 4, new DateTime(2028, 10, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1945), null, "Towar Testowy nr 33", 26.9443617203983m },
                    { 34, 5, new DateTime(2028, 11, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1950), null, "Towar Testowy nr 34", 43.5850088345587m },
                    { 35, 1, new DateTime(2028, 12, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1954), null, "Towar Testowy nr 35", 31.6123866277649m },
                    { 36, 2, new DateTime(2029, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1957), null, "Towar Testowy nr 36", 46.3792191329492m },
                    { 37, 3, new DateTime(2029, 2, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1963), null, "Towar Testowy nr 37", 72.9894098715166m },
                    { 38, 4, new DateTime(2029, 3, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1966), null, "Towar Testowy nr 38", 39.4024450477197m },
                    { 39, 5, new DateTime(2029, 4, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1970), null, "Towar Testowy nr 39", 44.9792045939076m },
                    { 40, 1, new DateTime(2029, 5, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(1973), null, "Towar Testowy nr 40", 78.1800942787078m },
                    { 41, 2, new DateTime(2029, 6, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2042), null, "Towar Testowy nr 41", 91.2486661532264m },
                    { 42, 3, new DateTime(2029, 7, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2048), null, "Towar Testowy nr 42", 36.0033799899872m },
                    { 43, 4, new DateTime(2029, 8, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2051), null, "Towar Testowy nr 43", 58.9933778599974m },
                    { 44, 5, new DateTime(2029, 9, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2055), null, "Towar Testowy nr 44", 38.3331796130647m },
                    { 45, 1, new DateTime(2029, 10, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2061), null, "Towar Testowy nr 45", 96.2303072303264m },
                    { 46, 2, new DateTime(2029, 11, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2064), null, "Towar Testowy nr 46", 54.6434516202231m },
                    { 47, 3, new DateTime(2029, 12, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2068), null, "Towar Testowy nr 47", 87.0685483478416m },
                    { 48, 4, new DateTime(2030, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2071), null, "Towar Testowy nr 48", 43.2615421031639m },
                    { 49, 5, new DateTime(2030, 2, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2077), null, "Towar Testowy nr 49", 63.7536367454758m },
                    { 50, 1, new DateTime(2030, 3, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2080), null, "Towar Testowy nr 50", 106.910075210218m },
                    { 51, 2, new DateTime(2030, 4, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2083), null, "Towar Testowy nr 51", 103.402609804063m },
                    { 52, 3, new DateTime(2030, 5, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2087), null, "Towar Testowy nr 52", 57.9550628700516m },
                    { 53, 4, new DateTime(2030, 6, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2092), null, "Towar Testowy nr 53", 89.1432228282673m },
                    { 54, 5, new DateTime(2030, 7, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2096), null, "Towar Testowy nr 54", 27.5923807630396m },
                    { 55, 1, new DateTime(2030, 8, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2099), null, "Towar Testowy nr 55", 10.3155852306541m },
                    { 56, 2, new DateTime(2030, 9, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2103), null, "Towar Testowy nr 56", 34.4710910471286m },
                    { 57, 3, new DateTime(2030, 10, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2108), null, "Towar Testowy nr 57", 82.7399933908135m },
                    { 58, 4, new DateTime(2030, 11, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2112), null, "Towar Testowy nr 58", 89.7783915545923m },
                    { 59, 5, new DateTime(2030, 12, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2115), null, "Towar Testowy nr 59", 102.853564832415m },
                    { 60, 1, new DateTime(2031, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2119), null, "Towar Testowy nr 60", 87.5752133974468m },
                    { 61, 2, new DateTime(2031, 2, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2124), null, "Towar Testowy nr 61", 23.2835097452838m },
                    { 62, 3, new DateTime(2031, 3, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2128), null, "Towar Testowy nr 62", 24.8266754934236m },
                    { 63, 4, new DateTime(2031, 4, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2131), null, "Towar Testowy nr 63", 90.7178217533794m },
                    { 64, 5, new DateTime(2031, 5, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2135), null, "Towar Testowy nr 64", 105.541105863811m },
                    { 65, 1, new DateTime(2031, 6, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2140), null, "Towar Testowy nr 65", 71.4668618332382m },
                    { 66, 2, new DateTime(2031, 7, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2146), null, "Towar Testowy nr 66", 90.1803267813444m },
                    { 67, 3, new DateTime(2031, 8, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2149), null, "Towar Testowy nr 67", 21.8390835882563m },
                    { 68, 4, new DateTime(2031, 9, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2153), null, "Towar Testowy nr 68", 29.9832426006689m },
                    { 69, 5, new DateTime(2031, 10, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2158), null, "Towar Testowy nr 69", 83.451929798758m },
                    { 70, 1, new DateTime(2031, 11, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2162), null, "Towar Testowy nr 70", 28.5010653967063m },
                    { 71, 2, new DateTime(2031, 12, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2165), null, "Towar Testowy nr 71", 24.0919714407404m },
                    { 72, 3, new DateTime(2032, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2168), null, "Towar Testowy nr 72", 84.4327442630165m },
                    { 73, 4, new DateTime(2032, 2, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2174), null, "Towar Testowy nr 73", 53.5432266979424m },
                    { 74, 5, new DateTime(2032, 3, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2177), null, "Towar Testowy nr 74", 13.3827639281976m },
                    { 75, 1, new DateTime(2032, 4, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2181), null, "Towar Testowy nr 75", 15.2131975374003m },
                    { 76, 2, new DateTime(2032, 5, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2235), null, "Towar Testowy nr 76", 43.4910578865107m },
                    { 77, 3, new DateTime(2032, 6, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2241), null, "Towar Testowy nr 77", 68.1794917145062m },
                    { 78, 4, new DateTime(2032, 7, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2244), null, "Towar Testowy nr 78", 78.7540496717082m },
                    { 79, 5, new DateTime(2032, 8, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2248), null, "Towar Testowy nr 79", 109.543323754364m },
                    { 80, 1, new DateTime(2032, 9, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2251), null, "Towar Testowy nr 80", 95.2857323946832m },
                    { 81, 2, new DateTime(2032, 10, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2257), null, "Towar Testowy nr 81", 24.5336427330319m },
                    { 82, 3, new DateTime(2032, 11, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2260), null, "Towar Testowy nr 82", 77.4757275212788m },
                    { 83, 4, new DateTime(2032, 12, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2264), null, "Towar Testowy nr 83", 15.2800884104758m },
                    { 84, 5, new DateTime(2033, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2268), null, "Towar Testowy nr 84", 83.9619183698128m },
                    { 85, 1, new DateTime(2033, 2, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2273), null, "Towar Testowy nr 85", 34.1238394888885m },
                    { 86, 2, new DateTime(2033, 3, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2276), null, "Towar Testowy nr 86", 51.0893742812264m },
                    { 87, 3, new DateTime(2033, 4, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2280), null, "Towar Testowy nr 87", 16.6500537945502m },
                    { 88, 4, new DateTime(2033, 5, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2283), null, "Towar Testowy nr 88", 31.6947851161281m },
                    { 89, 5, new DateTime(2033, 6, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2288), null, "Towar Testowy nr 89", 72.4018654948101m },
                    { 90, 1, new DateTime(2033, 7, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2292), null, "Towar Testowy nr 90", 44.025575247861m },
                    { 91, 2, new DateTime(2033, 8, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2295), null, "Towar Testowy nr 91", 101.277697018821m },
                    { 92, 3, new DateTime(2033, 9, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2299), null, "Towar Testowy nr 92", 63.6486290689557m },
                    { 93, 4, new DateTime(2033, 10, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2304), null, "Towar Testowy nr 93", 83.8189247558677m },
                    { 94, 5, new DateTime(2033, 11, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2308), null, "Towar Testowy nr 94", 104.162512358636m },
                    { 95, 1, new DateTime(2033, 12, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2311), null, "Towar Testowy nr 95", 54.8065536137609m },
                    { 96, 2, new DateTime(2034, 1, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2314), null, "Towar Testowy nr 96", 84.7467861136292m },
                    { 97, 3, new DateTime(2034, 2, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2320), null, "Towar Testowy nr 97", 39.0822732317024m },
                    { 98, 4, new DateTime(2034, 3, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2323), null, "Towar Testowy nr 98", 44.8067012633719m },
                    { 99, 5, new DateTime(2034, 4, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2327), null, "Towar Testowy nr 99", 21.378137262689m },
                    { 100, 1, new DateTime(2034, 5, 9, 23, 55, 22, 22, DateTimeKind.Local).AddTicks(2331), null, "Towar Testowy nr 100", 87.2474333042759m }
                });
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 19);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 20);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 21);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 22);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 23);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 24);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 25);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 26);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 27);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 28);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 29);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 30);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 31);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 32);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 33);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 34);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 35);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 36);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 37);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 38);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 39);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 40);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 41);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 42);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 43);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 44);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 45);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 46);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 47);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 48);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 49);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 50);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 51);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 52);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 53);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 54);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 55);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 56);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 57);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 58);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 59);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 60);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 61);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 62);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 63);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 64);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 65);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 66);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 67);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 68);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 69);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 70);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 71);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 72);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 73);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 74);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 75);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 76);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 77);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 78);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 79);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 80);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 81);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 82);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 83);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 84);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 85);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 86);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 87);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 88);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 89);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 90);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 91);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 92);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 93);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 94);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 95);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 96);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 97);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 98);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 99);

            migrationBuilder.DeleteData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 100);

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 1,
                column: "ExpirationDate",
                value: new DateTime(2028, 1, 9, 21, 16, 48, 25, DateTimeKind.Local).AddTicks(9326));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 2,
                column: "ExpirationDate",
                value: new DateTime(2029, 1, 9, 21, 16, 48, 25, DateTimeKind.Local).AddTicks(9377));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 3,
                column: "ExpirationDate",
                value: new DateTime(2031, 1, 9, 21, 16, 48, 25, DateTimeKind.Local).AddTicks(9380));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 4,
                column: "ExpirationDate",
                value: new DateTime(2036, 1, 9, 21, 16, 48, 25, DateTimeKind.Local).AddTicks(9382));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 5,
                column: "ExpirationDate",
                value: new DateTime(2026, 1, 16, 21, 16, 48, 25, DateTimeKind.Local).AddTicks(9385));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 6,
                column: "ExpirationDate",
                value: new DateTime(2026, 1, 12, 21, 16, 48, 25, DateTimeKind.Local).AddTicks(9388));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 7,
                column: "ExpirationDate",
                value: new DateTime(2027, 1, 9, 21, 16, 48, 25, DateTimeKind.Local).AddTicks(9391));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 8,
                column: "ExpirationDate",
                value: new DateTime(2026, 7, 9, 21, 16, 48, 25, DateTimeKind.Local).AddTicks(9393));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 9,
                column: "ExpirationDate",
                value: new DateTime(2027, 1, 9, 21, 16, 48, 25, DateTimeKind.Local).AddTicks(9396));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 10,
                column: "ExpirationDate",
                value: new DateTime(2028, 1, 9, 21, 16, 48, 25, DateTimeKind.Local).AddTicks(9398));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 11,
                column: "ExpirationDate",
                value: new DateTime(2029, 1, 9, 21, 16, 48, 25, DateTimeKind.Local).AddTicks(9401));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 12,
                column: "ExpirationDate",
                value: new DateTime(2031, 1, 9, 21, 16, 48, 25, DateTimeKind.Local).AddTicks(9404));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 13,
                columns: new[] { "ExpirationDate", "ImageName" },
                values: new object[] { new DateTime(2028, 1, 9, 21, 16, 48, 25, DateTimeKind.Local).AddTicks(9406), "płyn_naczynia.jpg" });

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 14,
                column: "ExpirationDate",
                value: new DateTime(2027, 1, 9, 21, 16, 48, 25, DateTimeKind.Local).AddTicks(9408));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 15,
                column: "ExpirationDate",
                value: new DateTime(2028, 1, 9, 21, 16, 48, 25, DateTimeKind.Local).AddTicks(9411));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 16,
                column: "ExpirationDate",
                value: new DateTime(2031, 1, 9, 21, 16, 48, 25, DateTimeKind.Local).AddTicks(9414));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 17,
                column: "ExpirationDate",
                value: new DateTime(2036, 1, 9, 21, 16, 48, 25, DateTimeKind.Local).AddTicks(9416));

            migrationBuilder.UpdateData(
                table: "Articles",
                keyColumn: "Id",
                keyValue: 18,
                column: "ExpirationDate",
                value: new DateTime(2030, 1, 9, 21, 16, 48, 25, DateTimeKind.Local).AddTicks(9418));
        }
    }
}

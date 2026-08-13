const routeButton =
    document.getElementById("routeButton");

const sourceSelect =
    document.getElementById("source");

const destinationSelect =
    document.getElementById("destination");

const algorithmSelect =
    document.getElementById("algorithm");

const result =
    document.getElementById("result");


async function getRoute(
    source,
    destination,
    algorithm
) {

    const url =
        "http://localhost:8080/route"
        + "?source="
        + encodeURIComponent(source)
        + "&destination="
        + encodeURIComponent(destination)
        + "&algorithm="
        + encodeURIComponent(algorithm);


    const response =
        await fetch(url);


    const data =
        await response.json();


    if (!response.ok) {

        throw new Error(
            data.error ||
            "Unable to calculate route."
        );
    }


    return data;
}


/* ==========================================
   DISPLAY TRAFFIC
   ========================================== */

function buildTrafficHTML(traffic) {

    let trafficHTML = "";


    if (!traffic) {

        return "";
    }


    traffic.forEach(
        function (item) {

            const level =
                item.level.toLowerCase();


            let icon = "🟢";


            if (level === "medium") {

                icon = "🟡";

            } else if (level === "high") {

                icon = "🟠";

            } else if (level === "severe") {

                icon = "🔴";
            }


            trafficHTML += `

                <div class="traffic-card ${level}">

                    <div class="traffic-road">

                        ${icon}
                        ${item.source}
                        →
                        ${item.destination}

                    </div>

                    <div class="traffic-info">

                        ${item.level}
                        |
                        ${item.time.toFixed(1)}
                        min

                    </div>

                </div>

            `;
        }
    );


    return trafficHTML;
}


/* ==========================================
   DISPLAY NORMAL RESULT
   ========================================== */

function displayResult(data) {

    let alternativeHTML = "";


    if (data.alternativeRoute) {

        alternativeHTML = `

            <div class="alternative-box">

                <h3>
                    Alternative Route
                </h3>

                <div class="alternative-route">

                    ${data.alternativeRoute.join(" → ")}

                </div>

                <div>

                    Estimated Time:
                    <strong>
                        ${data.alternativeTime.toFixed(1)}
                        min
                    </strong>

                </div>

            </div>


            <div class="time-saved">

                This route saves

                <strong>
                    ${Math.max(
                        0,
                        data.timeSaved
                    ).toFixed(1)}
                    minutes
                </strong>

                compared with the alternative route.

            </div>

        `;
    }


    const trafficHTML =
        buildTrafficHTML(data.traffic);


    result.innerHTML = `

        <div class="result-header">

            <h2>
                Route Result
            </h2>

            <div class="success">

                ✓ Traffic-optimized route
                calculated successfully.

            </div>

        </div>


        <div class="route-box">

            <h3>
                Optimal Route
            </h3>


            <div class="route-path">

                ${data.route.join(" → ")}

            </div>


            <div class="metrics">


                <div class="metric">

                    <div class="metric-label">
                        Distance
                    </div>

                    <div class="metric-value">

                        ${data.distance.toFixed(1)}
                        km

                    </div>

                </div>


                <div class="metric">

                    <div class="metric-label">
                        Estimated Time
                    </div>

                    <div class="metric-value">

                        ${data.time.toFixed(1)}
                        min

                    </div>

                </div>


                <div class="metric">

                    <div class="metric-label">
                        Algorithm
                    </div>

                    <div class="metric-value">

                        ${
                            data.algorithm.toLowerCase()
                            === "astar"
                            ? "A*"
                            : "Dijkstra"
                        }

                    </div>

                </div>


            </div>

        </div>


        ${alternativeHTML}


        <div class="traffic-section">

            <h3>
                Current Traffic Conditions
            </h3>

            <p>
                Live traffic conditions used
                by the routing algorithm.
            </p>


            <div class="traffic-grid">

                ${trafficHTML}

            </div>

        </div>


        <div class="comparison-section">

            <button
                id="compareButton"
                class="compare-button">

                Compare Dijkstra vs A*

            </button>

        </div>

    `;
}


/* ==========================================
   FIND BEST ROUTE
   ========================================== */

routeButton.addEventListener(
    "click",
    async function () {


        const source =
            sourceSelect.value;


        const destination =
            destinationSelect.value;


        const algorithm =
            algorithmSelect.value;


        if (source === destination) {

            result.innerHTML = `

                <div class="empty-result">

                    <div class="empty-icon">
                        ⚠️
                    </div>

                    <h2>
                        Invalid Selection
                    </h2>

                    <p>
                        Starting location and
                        destination must be different.
                    </p>

                </div>

            `;

            return;
        }


        result.innerHTML = `

            <div class="empty-result">

                <div class="empty-icon">
                    🚦
                </div>

                <h2>
                    Finding Best Route...
                </h2>

                <p>
                    Java is calculating the
                    traffic-optimized route.
                </p>

            </div>

        `;


        try {

            const data =
                await getRoute(
                    source,
                    destination,
                    algorithm
                );


            displayResult(data);


            addComparisonListener(
                source,
                destination
            );


        } catch (error) {

            console.error(error);


            result.innerHTML = `

                <div class="empty-result">

                    <div class="empty-icon">
                        ❌
                    </div>

                    <h2>
                        Connection Error
                    </h2>

                    <p>
                        Java server is not running.
                        Please start RouteServer.java
                        and try again.
                    </p>

                </div>

            `;
        }

    }
);


/* ==========================================
   ADD COMPARISON BUTTON
   ========================================== */

function addComparisonListener(
    source,
    destination
) {

    const compareButton =
        document.getElementById(
            "compareButton"
        );


    if (!compareButton) {

        return;
    }


    compareButton.addEventListener(
        "click",
        async function () {

            compareButton.disabled =
                true;


            compareButton.innerText =
                "Comparing Algorithms...";


            try {

                const results =
                    await Promise.all([

                        getRoute(
                            source,
                            destination,
                            "dijkstra"
                        ),

                        getRoute(
                            source,
                            destination,
                            "astar"
                        )

                    ]);


                displayComparison(
                    results[0],
                    results[1]
                );


            } catch (error) {

                console.error(error);


                compareButton.disabled =
                    false;

                compareButton.innerText =
                    "Compare Dijkstra vs A*";

            }

        }
    );
}


/* ==========================================
   DISPLAY COMPARISON
   ========================================== */

function displayComparison(
    dijkstra,
    astar
) {

    const dijkstraRoute =
        dijkstra.route.join(" → ");


    const astarRoute =
        astar.route.join(" → ");


    const dijkstraTime =
        dijkstra.time.toFixed(1);


    const astarTime =
        astar.time.toFixed(1);


    const fasterAlgorithm =
        Number(dijkstra.time)
        <= Number(astar.time)
            ? "Dijkstra"
            : "A*";


    result.innerHTML += `

        <div class="comparison-box">

            <h2>
                Algorithm Comparison
            </h2>


            <p>
                Both algorithms were tested
                using the same traffic conditions.
            </p>


            <div class="comparison-grid">


                <div class="comparison-card">

                    <h3>
                        Dijkstra
                    </h3>

                    <p class="comparison-route">

                        ${dijkstraRoute}

                    </p>

                    <p>

                        Distance:
                        <strong>
                            ${dijkstra.distance.toFixed(1)}
                            km
                        </strong>

                    </p>

                    <p>

                        Time:
                        <strong>
                            ${dijkstraTime}
                            min
                        </strong>

                    </p>

                </div>


                <div class="comparison-card">

                    <h3>
                        A* Algorithm
                    </h3>

                    <p class="comparison-route">

                        ${astarRoute}

                    </p>

                    <p>

                        Distance:
                        <strong>
                            ${astar.distance.toFixed(1)}
                            km
                        </strong>

                    </p>

                    <p>

                        Time:
                        <strong>
                            ${astarTime}
                            min
                        </strong>

                    </p>

                </div>


            </div>


            <div class="comparison-result">

                Faster result:

                <strong>
                    ${fasterAlgorithm}
                </strong>

            </div>

        </div>

    `;
}
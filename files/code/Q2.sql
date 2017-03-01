    SELECT
        @ac := avg(Carsales),
        @ab := avg(bs),
        @sc := sum(Carsales),
        @sb := sum(bs),
        @cma := Carsales

        FROM(
            SELECT
                count(*) AS Carsales,
                gasc.Barrels_sold AS bs
                
                FROM
                    cars
                    INNER JOIN
                        gasc
                    ON
                        YEARWEEK(`Sale_date`) = YEARWEEK(`Date`)    
                    GROUP BY
                        YEARWEEK(`Sale_date`)             
            )AS blah;


    SELECT 
        @csac := CarSales-@ac,
        @bsab := bs-@ab,
        @csacs := sum((CarSales-@ac)*(CarSales-@ac)),      
        @bsabs := sum((bs-@ab)*(bs-@ab)),
        @csactbsab := sum(((CarSales-@ac)*(bs-@ab)))

        FROM(
            SELECT
                count(*) AS CarSales, 
                g.Barrels_sold AS bs
            
                FROM    
                    cars AS c
                    INNER JOIN
                        gasc AS g
                    ON 
                        YEARWEEK(`Sale_date`) = YEARWEEK(`Date`)    
                    GROUP BY
                        YEARWEEK(`Sale_date`)
            )AS blah;

    SELECT
        TRUNCATE(@csactbsab,0) AS "(X-Mx)(Y-My)",
        TRUNCATE(@bsabs ,0) AS "SUM(X-Mx)^2",
        TRUNCATE(@csacs,0) AS "SUM(Y-My)^2",
        TRUNCATE(@csactbsab / SQRT(@bsabs * @csacs),4) AS "r";